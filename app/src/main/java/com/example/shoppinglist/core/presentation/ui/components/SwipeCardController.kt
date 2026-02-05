package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SwipeCardController {
    var activeCardId by mutableStateOf<Any?>(null)
}
