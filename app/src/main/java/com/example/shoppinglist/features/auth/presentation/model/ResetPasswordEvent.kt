package com.example.shoppinglist.features.auth.presentation.model

sealed interface ResetPasswordEvent {
    object ResetPasswordClick : ResetPasswordEvent
}