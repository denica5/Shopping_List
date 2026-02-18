package com.example.shoppinglist.features.listDetailScreen.presentation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ListDetailState(
    val products: ImmutableList<ProductUi> = persistentListOf(),
    val sortMode: SortMode = SortMode.ALPHABETICAL,
    val activeSheet: ListDetailSheet? = null,
    val isSortSubmenuVisible: Boolean = false,
    val isDeleteAllDialogVisible: Boolean = false,
    val isClearPurchasedDialogVisible: Boolean = false,
    val editingProduct: ProductUi? = null,
    val inputName: String = "",
    val inputQuantity: String = "",
    val inputUnit: ProductUnit? = null,
)