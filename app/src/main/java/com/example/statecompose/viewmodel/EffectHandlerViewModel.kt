package com.example.statecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EffectHandlerViewModel : ViewModel() {
    private val _nameState = MutableStateFlow("")
    val nameState = _nameState.asStateFlow()

    fun loadName() {
        viewModelScope.launch {
            println("loadName()")
            delay(1500L)
            _nameState.update { "Chee Siong" }
        }
    }

    fun changeName() {
        val list = listOf("Caesium", "Jane", "Jocelyn", "Georgia", "Kane", "Aaron", "Kobe", "Pal", "Leicester", "Xavier")
        println("changeName()")
        _nameState.update { list.random() }
    }
}
