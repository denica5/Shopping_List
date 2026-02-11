package com.example.shoppinglist.features.listDetailScreen.presentation

import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailAction
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailEvent
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailState
import com.example.shoppinglist.features.listDetailScreen.presentation.model.Product_tmp
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ProductUnit
import com.example.shoppinglist.features.listDetailScreen.presentation.model.SortMode
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.update

@HiltViewModel
// TODO: при подключении БД настроить инжекты useCase-ов в конструкторе
class ListDetailViewModel @Inject constructor() :
    BaseViewModel<ListDetailEvent, ListDetailState, ListDetailAction>(ListDetailState()) {

    override val tag: String = "ListDetailViewModel"

    // TODO: мок-данные — убрать при подключении БД
    private var nextId = 5

    // TODO: по готовности БД добавить init-блок с подпиской на flow из БД

    override fun obtainEvent(event: ListDetailEvent) {
        when (event) {
            is ListDetailEvent.AddProductClick -> openAddSheet()
            is ListDetailEvent.EditProductClick -> openEditSheet(event.product)
            is ListDetailEvent.SaveProductClick -> saveProduct()
            is ListDetailEvent.DismissAddEditSheet -> dismissAddEditSheet()
            is ListDetailEvent.InputNameChanged -> updateInputName(event.name)
            is ListDetailEvent.InputQuantityChanged -> updateInputQuantity(event.quantity)
            is ListDetailEvent.InputUnitChanged -> updateInputUnit(event.unit)
            is ListDetailEvent.IncrementQuantity -> incrementQuantity()
            is ListDetailEvent.DecrementQuantity -> decrementQuantity()
            is ListDetailEvent.TogglePurchased -> togglePurchased(event.product)
            is ListDetailEvent.DeleteProduct -> deleteProduct(event.product)
            is ListDetailEvent.MoveProduct -> moveProduct(event.fromIndex, event.toIndex)
            is ListDetailEvent.MenuClick -> showMenu()
            is ListDetailEvent.DismissMenuSheet -> dismissMenu()
            is ListDetailEvent.ToggleSortSubmenu -> toggleSortSubmenu()
            is ListDetailEvent.SetSortMode -> setSortMode(event.sortMode)
            is ListDetailEvent.DeleteAllClick -> showDeleteAllDialog()
            is ListDetailEvent.ClearPurchasedClick -> showClearPurchasedDialog()
            is ListDetailEvent.ConfirmDeleteAll -> confirmDeleteAll()
            is ListDetailEvent.DismissDeleteAllDialog -> dismissDeleteAllDialog()
            is ListDetailEvent.ConfirmClearPurchased -> confirmClearPurchased()
            is ListDetailEvent.DismissClearPurchasedDialog -> dismissClearPurchasedDialog()
            is ListDetailEvent.BackClick -> {
                _action.update { ListDetailAction.NavigateBack }
            }
        }
    }

    fun clearAction() {
        _action.update { null }
    }

    private fun openAddSheet() {
        _state.update {
            it.copy(
                isAddEditSheetVisible = true,
                editingProduct = null,
                inputName = "",
                inputQuantity = "",
                inputUnit = null,
            )
        }
    }

    private fun openEditSheet(product: Product_tmp) {
        _state.update {
            it.copy(
                isAddEditSheetVisible = true,
                editingProduct = product,
                inputName = product.name,
                inputQuantity = formatQuantity(product.quantity),
                inputUnit = product.unit,
            )
        }
    }

    private fun saveProduct() {
        val currentState = _state.value
        val name = currentState.inputName.trim()
        if (name.isEmpty()) return

        val quantity = currentState.inputQuantity.toDoubleOrNull() ?: 0.0
        val unit = currentState.inputUnit

        val editingProduct = currentState.editingProduct
        if (editingProduct != null) {
            // TODO: заменить на updateProductUseCase(product)
            val updatedProducts = currentState.products.map { product ->
                if (product.id == editingProduct.id) {
                    product.copy(
                        name = name,
                        quantity = quantity,
                        unit = unit,
                    )
                } else {
                    product
                }
            }
            _state.update {
                it.copy(
                    products = applySorting(updatedProducts, it.sortMode),
                    isAddEditSheetVisible = false,
                    editingProduct = null,
                )
            }
        } else {
            // TODO: заменить на createProductUseCase(product)
            val newProduct = Product_tmp(
                id = nextId++,
                name = name,
                quantity = quantity,
                unit = unit,
            )
            val updatedProducts = currentState.products + newProduct
            _state.update {
                it.copy(
                    products = applySorting(updatedProducts, it.sortMode),
                    isAddEditSheetVisible = false,
                )
            }
        }
    }

    private fun dismissAddEditSheet() {
        _state.update {
            it.copy(
                isAddEditSheetVisible = false,
                editingProduct = null,
            )
        }
    }

    private fun updateInputName(name: String) {
        _state.update { it.copy(inputName = name) }
    }

    private fun updateInputQuantity(quantity: String) {
        _state.update { it.copy(inputQuantity = quantity) }
    }

    private fun updateInputUnit(unit: ProductUnit) {
        _state.update { it.copy(inputUnit = unit) }
    }

    private fun incrementQuantity() {
        val currentState = _state.value
        val current = currentState.inputQuantity.toDoubleOrNull() ?: 0.0
        val step = currentState.inputUnit?.step ?: 1.0
        val newValue = current + step
        _state.update { it.copy(inputQuantity = formatQuantity(newValue)) }
    }

    private fun decrementQuantity() {
        val currentState = _state.value
        val current = currentState.inputQuantity.toDoubleOrNull() ?: 0.0
        val step = currentState.inputUnit?.step ?: 1.0
        val newValue = (current - step).coerceAtLeast(0.0)
        _state.update { it.copy(inputQuantity = formatQuantity(newValue)) }
    }

    // TODO: заменить на updateProductUseCase(product.copy(isPurchased = !product.isPurchased)).
    private fun togglePurchased(product: Product_tmp) {
        _state.update { state ->
            state.copy(
                products = state.products.map {
                    if (it.id == product.id) it.copy(isPurchased = !it.isPurchased) else it
                }
            )
        }
    }

    // TODO: заменить на deleteProductUseCase(product.id)
    private fun deleteProduct(product: Product_tmp) {
        _state.update { state ->
            state.copy(
                products = state.products.filter { it.id != product.id }
            )
        }
    }

    // TODO: после перемещения сохранить новый порядок в БД
    private fun moveProduct(fromIndex: Int, toIndex: Int) {
        _state.update { state ->
            val mutableList = state.products.toMutableList()
            val item = mutableList.removeAt(fromIndex)
            mutableList.add(toIndex, item)
            state.copy(products = mutableList)
        }
    }

    private fun showMenu() {
        _state.update {
            it.copy(
                isMenuSheetVisible = true,
                isSortSubmenuVisible = false,
            )
        }
    }

    private fun dismissMenu() {
        _state.update {
            it.copy(
                isMenuSheetVisible = false,
                isSortSubmenuVisible = false,
            )
        }
    }

    private fun toggleSortSubmenu() {
        _state.update { it.copy(isSortSubmenuVisible = !it.isSortSubmenuVisible) }
    }

    // TODO: сортировать либо через БД, либо через UseCase
    private fun setSortMode(sortMode: SortMode) {
        _state.update { state ->
            state.copy(
                sortMode = sortMode,
                products = applySorting(state.products, sortMode),
                isSortSubmenuVisible = false,
                isMenuSheetVisible = false,
            )
        }
    }

    private fun showDeleteAllDialog() {
        _state.update {
            it.copy(
                isDeleteAllDialogVisible = true,
                isMenuSheetVisible = false,
            )
        }
    }

    private fun dismissDeleteAllDialog() {
        _state.update { it.copy(isDeleteAllDialogVisible = false) }
    }

    // TODO: заменить на deleteAllProductsUseCase(listId).
    private fun confirmDeleteAll() {
        _state.update {
            it.copy(
                products = emptyList(),
                isDeleteAllDialogVisible = false,
            )
        }
    }

    private fun showClearPurchasedDialog() {
        _state.update {
            it.copy(
                isClearPurchasedDialogVisible = true,
                isMenuSheetVisible = false,
            )
        }
    }

    private fun dismissClearPurchasedDialog() {
        _state.update { it.copy(isClearPurchasedDialogVisible = false) }
    }

    // TODO: заменить на deletePurchasedProductsUseCase(listId)
    private fun confirmClearPurchased() {
        _state.update { state ->
            state.copy(
                products = state.products.filter { !it.isPurchased },
                isClearPurchasedDialogVisible = false,
            )
        }
    }

    private fun applySorting(products: List<Product_tmp>, sortMode: SortMode): List<Product_tmp> {
        return when (sortMode) {
            SortMode.ALPHABETICAL -> products.sortedBy { it.name.lowercase() }
            SortMode.CUSTOM -> products
        }
    }

    private fun formatQuantity(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}