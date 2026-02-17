package com.example.shoppinglist.features.auth.presentation.model

import com.example.shoppinglist.core.utils.UIText

sealed interface RegisterAction {
    object NavigateToLogin : RegisterAction
    data class ShowFormError(val message: UIText) : RegisterAction
}