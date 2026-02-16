package com.example.shoppinglist.features.auth.presentation.model

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val passwordCheck: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {
}