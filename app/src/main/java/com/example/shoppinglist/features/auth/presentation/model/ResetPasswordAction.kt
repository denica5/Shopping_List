package com.example.shoppinglist.features.auth.presentation.model

sealed interface ResetPasswordAction {
    object NavigateToLogin : ResetPasswordAction
}