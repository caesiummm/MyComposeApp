package com.example.statecompose.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Small font",
    group = "Font scales",
    fontScale = 0.5f
)
@Preview(
    name = "Medium font",
    group = "Font scales",
    fontScale = 1.0f
)
@Preview(
    name = "Large font",
    group = "Font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@Preview(
    name = "Light mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Dark mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
annotation class DarkLightModePreviews