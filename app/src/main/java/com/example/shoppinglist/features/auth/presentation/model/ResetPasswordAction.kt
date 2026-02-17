package com.example.shoppinglist.features.auth.presentation.model

import com.example.shoppinglist.core.utils.UIText

sealed interface ResetPasswordAction {
    object NavigateToLogin : ResetPasswordAction
    data class ShowFormError(val message: UIText) : ResetPasswordAction
}