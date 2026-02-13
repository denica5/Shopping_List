package com.example.shoppinglist.features.listDetailScreen.presentation.model

sealed interface ListDetailAction {
    object NavigateBack : ListDetailAction
}