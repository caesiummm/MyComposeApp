package com.example.statecompose.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

/**
 * 写一个类，继承FlowTest，比如默认的FlowTestImpl1实现
 * 需要根据播放器的播放，每隔20毫秒，提供当前的播放器进度（注意，播放器是全局的，只能提供当前FlowTest的播放进度）
 *
 * main 方法可以直接跑，如果输出的progress是正常递增的就说明程序至少是能跑，做完后替换下面的FlowTestImpl1创建
 */
@OptIn(DelicateCoroutinesApi::class)
fun main() {
    PlayerImpl.play(Msg(666L, "http://1"))
    /**
     * 做完后替换下面的FlowTestImpl1即可
     */
//    val flowTest: FlowTest = FlowTestImpl1()
    //比如
    val flowTest: FlowTest = MyFlowTestImpl()
    var startTime = System.currentTimeMillis()
    GlobalScope.launch {
        delay(1000)
        flowTest.play(Msg(1L, "http://1"))
    }
    GlobalScope.launch {
        flowTest.progress.collect { progress ->
            if (progress > 0 && System.currentTimeMillis() - startTime < 1000) {
                throw RuntimeException("程序有误，检查是否只打印自己的播放")
            }
            println("progress ==> $progress")
        }
    }
    Thread.sleep(5000)
}

interface FlowTest {
    val progress: Flow<Float>
    fun play(msg: Msg)
    fun stop()
}

class MyFlowTestImpl(val player: Player = PlayerImpl) : FlowTest {
    private val audioMsgFlow = MutableStateFlow(Msg(-1L, ""))

    override val progress = combine(
        audioMsgFlow,
        player.playingMsg,
        player.playerState,
        flow {
            while (true) {
                emit(Unit)
                delay(20)
            }
        }
    ) { audioMsg, playingMsg, playerState, _ ->
        if (audioMsg.msgId == playingMsg?.msgId && playerState == Player.PlayerState.Playing) {
            player.getProgress()
        } else {
            0f
        }
    }.distinctUntilChanged()

    override fun play(msg: Msg) {
        audioMsgFlow.value = msg
        player.play(msg)
    }

    override fun stop() {
        audioMsgFlow.value = Msg(-1L, "")
        player.stop()
    }
}

/**
 * 下面的代码是为了能够跑起来写的，不要改
 */
interface Player {

    val playerState: StateFlow<PlayerState>
    val playingMsg: StateFlow<Msg?>

    enum class PlayerState {
        Idle,
        Buffering,
        Playing,
    }

    fun play(msg: Msg)

    fun stop()

    fun getProgress(): Float

}


object PlayerImpl : Player {
    private sealed interface Action {
        class Play(val msg: Msg) : Action
        object Stop : Action
    }

    private val playerContext = newSingleThreadContext("player")
    private val scope = CoroutineScope(SupervisorJob() + playerContext)
    override val playingMsg = MutableStateFlow<Msg?>(null)
    override val playerState = MutableStateFlow(Player.PlayerState.Idle)

    //from 0f to 1f
    @Volatile
    private var progress: Float = 0f
    private val actionQueue = MutableSharedFlow<Action>(1)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            actionQueue.collect { action ->
                when (action) {
                    is Action.Play -> bufferAndPlay(action.msg)
                    Action.Stop -> stopInternal()
                }
            }
        }
    }


    override fun play(msg: Msg) {
        scope.launch {
            actionQueue.emit(Action.Play(msg))
        }
    }

    override fun stop() {
        scope.launch {
            actionQueue.emit(Action.Stop)
        }
    }

    override fun getProgress(): Float {
        return progress
    }

    private fun stopInternal() {
        progress = 0f
        playingMsg.value = null
        playerState.value = Player.PlayerState.Idle
    }

    private suspend fun bufferInternal(msg: Msg) {
        progress = 0f
        playingMsg.value = msg
        playerState.value = Player.PlayerState.Buffering
        var bufferSize = 0
        while (currentCoroutineContext().isActive && bufferSize < 1000) {
            delay((1L..10L).random())
            bufferSize += 100
        }
    }

    private suspend fun playInternal(msg: Msg) {
        progress = 0f
        playingMsg.value = msg
        playerState.value = Player.PlayerState.Playing
        while (currentCoroutineContext().isActive && progress < 1f) {
            val random = (1L..100L).random()
            delay(random)
            progress += random / 1000f
        }
        stopInternal()
    }

    private suspend fun bufferAndPlay(msg: Msg) {
        bufferInternal(msg)
        playInternal(msg)
    }

}

data class Msg(val msgId: Long, val voiceUrl: String)