package com.example.shoppinglist.features.listDetailScreen.presentation.model

sealed interface ListDetailSheet {
    data object Menu : ListDetailSheet
    data object AddEdit : ListDetailSheet
}