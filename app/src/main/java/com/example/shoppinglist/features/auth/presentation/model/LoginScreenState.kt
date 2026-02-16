package com.example.shoppinglist.features.auth.presentation.model

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)