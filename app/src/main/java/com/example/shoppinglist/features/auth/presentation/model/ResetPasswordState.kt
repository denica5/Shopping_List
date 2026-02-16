package com.example.shoppinglist.features.auth.presentation.model

data class ResetPasswordState(
    val email: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {
}