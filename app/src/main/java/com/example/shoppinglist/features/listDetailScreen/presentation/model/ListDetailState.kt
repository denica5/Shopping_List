package com.example.shoppinglist.features.listDetailScreen.presentation.model

// TODO: по готовности БД — заменить Product_tmp на доменную модель
data class ListDetailState(
    val products: List<Product_tmp> = emptyList(),
    val sortMode: SortMode = SortMode.ALPHABETICAL,
    val activeSheet: ListDetailSheet? = null,
    val isSortSubmenuVisible: Boolean = false,
    val isDeleteAllDialogVisible: Boolean = false,
    val isClearPurchasedDialogVisible: Boolean = false,
    val editingProduct: Product_tmp? = null,
    val inputName: String = "",
    val inputQuantity: String = "",
    val inputUnit: ProductUnit? = null,
)