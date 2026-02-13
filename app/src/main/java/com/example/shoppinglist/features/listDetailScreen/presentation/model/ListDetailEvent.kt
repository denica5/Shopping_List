package com.example.shoppinglist.features.listDetailScreen.presentation.model

sealed interface ListDetailEvent {
    object AddProductClick : ListDetailEvent
    object SaveProductClick : ListDetailEvent
    object DismissSheet : ListDetailEvent
    data class EditProductClick(val product: Product_tmp) : ListDetailEvent
    data class InputNameChanged(val name: String) : ListDetailEvent
    data class InputQuantityChanged(val quantity: String) : ListDetailEvent
    data class InputUnitChanged(val unit: ProductUnit) : ListDetailEvent
    object IncrementQuantity : ListDetailEvent
    object DecrementQuantity : ListDetailEvent

    data class TogglePurchased(val product: Product_tmp) : ListDetailEvent
    data class DeleteProduct(val product: Product_tmp) : ListDetailEvent
    data class MoveProduct(val fromIndex: Int, val toIndex: Int) : ListDetailEvent

    object MenuClick : ListDetailEvent
    object ToggleSortSubmenu : ListDetailEvent
    data class SetSortMode(val sortMode: SortMode) : ListDetailEvent
    object DeleteAllClick : ListDetailEvent
    object ClearPurchasedClick : ListDetailEvent

    object ConfirmDeleteAll : ListDetailEvent
    object DismissDeleteAllDialog : ListDetailEvent
    object ConfirmClearPurchased : ListDetailEvent
    object DismissClearPurchasedDialog : ListDetailEvent

    object BackClick : ListDetailEvent
}