package com.example.shoppinglist.features.auth.presentation.model

import com.example.shoppinglist.core.utils.UIText

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val passwordCheck: String = "",
    val emailError: UIText? = null,
    val passwordError: UIText? = null,
    val formError: UIText? = null,
    val isLoading: Boolean = false
) {
}