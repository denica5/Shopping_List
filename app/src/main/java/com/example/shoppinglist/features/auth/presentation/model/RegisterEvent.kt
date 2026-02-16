package com.example.shoppinglist.features.auth.presentation.model

sealed interface RegisterEvent {
    object RegisterClick : RegisterEvent
}