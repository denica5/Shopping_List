package com.example.shoppinglist.features.auth.presentation.model

import com.example.shoppinglist.core.utils.UIText

interface LoginScreenAction {
    data object NavigateToProductList : LoginScreenAction
    data class ShowFormError(val message: UIText) : LoginScreenAction
}