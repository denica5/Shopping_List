package com.example.shoppinglist.features.auth.presentation.model

sealed interface RegisterAction {
    object NavigateToLogin : RegisterAction
}