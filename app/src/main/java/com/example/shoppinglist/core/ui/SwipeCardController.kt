package com.example.shoppinglist.core.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SwipeCardController {
    var activeCardId by mutableStateOf<Any?>(null)
}
