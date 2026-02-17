package com.example.shoppinglist.features.auth.presentation.model

import com.example.shoppinglist.core.utils.UIText

data class ResetPasswordState(
    val email: String = "",
    val emailError: UIText? = null,
    val formError: UIText? = null,
    val isLoading: Boolean = false
) {
}