package com.example.shoppinglist.core.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    data class StringResource(
        @param:StringRes val resId: Int
    ) : UIText()
}

@Composable
fun UIText.asString(): String {
    return when (this) {
        is UIText.DynamicString -> value
        is UIText.StringResource -> stringResource(resId)
    }
}