package com.example.shoppinglist.features.listDetailScreen.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.features.listDetailScreen.domain.entity.Product
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.CreateProductUseCase
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.DeleteAllProductsByListIdUseCase
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.DeleteCheckedProductsByListIdUseCase
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.DeleteProductByIdUseCase
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.GetProductsByListIdUseCase
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.ToggleProductCheckedUseCase
import com.example.shoppinglist.features.listDetailScreen.domain.usecase.UpdateProductUseCase
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailAction
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailEvent
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailSheet
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailState
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ProductUi
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ProductUnit
import com.example.shoppinglist.features.listDetailScreen.presentation.model.SortMode
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    private val getProductsByListIdUseCase: GetProductsByListIdUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
    private val deleteAllProductsByListIdUseCase: DeleteAllProductsByListIdUseCase,
    private val deleteCheckedProductsByListIdUseCase: DeleteCheckedProductsByListIdUseCase,
    private val toggleProductCheckedUseCase: ToggleProductCheckedUseCase,
) : BaseViewModel<ListDetailEvent, ListDetailState, ListDetailAction>(ListDetailState()) {

    override val tag: String = "ListDetailViewModel"
    private var currentListId: Int? = null
    private var productsObserverJob: Job? = null
    private var productsFromDb: List<ProductUi> = emptyList()

    override fun obtainEvent(event: ListDetailEvent) {
        when (event) {
            is ListDetailEvent.AddProductClick -> openAddSheet()
            is ListDetailEvent.EditProductClick -> openEditSheet(event.product)
            is ListDetailEvent.SaveProductClick -> saveProduct()
            is ListDetailEvent.DismissSheet -> dismissSheet()
            is ListDetailEvent.InputNameChanged -> updateInputName(event.name)
            is ListDetailEvent.InputQuantityChanged -> updateInputQuantity(event.quantity)
            is ListDetailEvent.InputUnitChanged -> updateInputUnit(event.unit)
            is ListDetailEvent.IncrementQuantity -> incrementQuantity()
            is ListDetailEvent.DecrementQuantity -> decrementQuantity()
            is ListDetailEvent.TogglePurchased -> togglePurchased(event.product)
            is ListDetailEvent.DeleteProduct -> deleteProduct(event.product)
            is ListDetailEvent.MoveProduct -> moveProduct(event.fromIndex, event.toIndex)
            is ListDetailEvent.MenuClick -> toggleMenu()
            is ListDetailEvent.ToggleSortSubmenu -> toggleSortSubmenu()
            is ListDetailEvent.SetSortMode -> setSortMode(event.sortMode)
            is ListDetailEvent.DeleteAllClick -> showDeleteAllDialog()
            is ListDetailEvent.ClearPurchasedClick -> showClearPurchasedDialog()
            is ListDetailEvent.ConfirmDeleteAll -> confirmDeleteAll()
            is ListDetailEvent.DismissDeleteAllDialog -> dismissDeleteAllDialog()
            is ListDetailEvent.ConfirmClearPurchased -> confirmClearPurchased()
            is ListDetailEvent.DismissClearPurchasedDialog -> dismissClearPurchasedDialog()
            is ListDetailEvent.BackClick -> {
                viewModelScope.launch { _action.emit(ListDetailAction.NavigateBack) }
            }
        }
    }

    fun setListId(listId: Int) {
        if (currentListId == listId) return
        currentListId = listId

        productsObserverJob?.cancel()
        productsObserverJob = viewModelScope.launch {
            getProductsByListIdUseCase(listId)
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    Log.e(tag, "Failed to load products for listId=$listId", error)
                }
                .collect { products ->
                    productsFromDb = products.map { it.toPresentation() }
                    _state.update { state ->
                        state.copy(
                            products = applySorting(productsFromDb, state.sortMode)
                        )
                    }
                }
        }
    }

    private fun openAddSheet() {
        _state.update {
            it.copy(
                activeSheet = ListDetailSheet.AddEdit,
                isSortSubmenuVisible = false,
                editingProduct = null,
                inputName = "",
                inputQuantity = "",
                inputUnit = null,
            )
        }
    }

    private fun openEditSheet(product: ProductUi) {
        _state.update {
            it.copy(
                activeSheet = ListDetailSheet.AddEdit,
                isSortSubmenuVisible = false,
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
        val listId = currentListId ?: return

        val quantity = currentState.inputQuantity.toDoubleOrNull() ?: 0.0
        val unit = currentState.inputUnit

        val editingProduct = currentState.editingProduct
        if (editingProduct != null) {
            runSafely(
                block = {
                    withContext(Dispatchers.IO) {
                        updateProductUseCase(
                            Product(
                                id = editingProduct.id,
                                listId = listId,
                                name = name,
                                quantity = quantity,
                                unit = unit?.name,
                                isChecked = editingProduct.isPurchased,
                                // Keep persisted custom order untouched when editing.
                                position = editingProduct.position,
                            )
                        )
                    }
                    _state.update {
                        it.copy(
                            activeSheet = null,
                            editingProduct = null,
                        )
                    }
                },
                onError = { error ->
                    Log.e(tag, "Failed to update product id=${editingProduct.id}", error)
                }
            )
        } else {
            runSafely(
                block = {
                    withContext(Dispatchers.IO) {
                        createProductUseCase(
                            Product(
                                listId = listId,
                                name = name,
                                quantity = quantity,
                                unit = unit?.name,
                                isChecked = false,
                                position = productsFromDb.size,
                            )
                        )
                    }
                    _state.update {
                        it.copy(
                            activeSheet = null,
                            editingProduct = null,
                        )
                    }
                },
                onError = { error ->
                    Log.e(tag, "Failed to create product for listId=$listId", error)
                }
            )
        }
    }

    private fun dismissSheet() {
        _state.update {
            it.copy(
                activeSheet = null,
                isSortSubmenuVisible = false,
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

    private fun togglePurchased(product: ProductUi) {
        runSafely(
            block = {
                withContext(Dispatchers.IO) {
                    toggleProductCheckedUseCase(product.id)
                }
            },
            onError = { error ->
                Log.e(tag, "Failed to toggle purchased for product id=${product.id}", error)
            }
        )
    }

    private fun deleteProduct(product: ProductUi) {
        runSafely(
            block = {
                withContext(Dispatchers.IO) {
                    deleteProductByIdUseCase(product.id)
                }
            },
            onError = { error ->
                Log.e(tag, "Failed to delete product id=${product.id}", error)
            }
        )
    }

    private fun moveProduct(fromIndex: Int, toIndex: Int) {
        val listId = currentListId ?: return
        val state = _state.value
        val mutableList = state.products.toMutableList()
        val item = mutableList.removeAt(fromIndex)
        mutableList.add(toIndex, item)
        _state.update { it.copy(products = mutableList.toPersistentList()) }

        runSafely(
            block = {
                withContext(Dispatchers.IO) {
                    mutableList.forEachIndexed { index, product ->
                        updateProductUseCase(
                            Product(
                                id = product.id,
                                listId = listId,
                                name = product.name,
                                quantity = product.quantity,
                                unit = product.unit?.name,
                                isChecked = product.isPurchased,
                                position = index,
                            )
                        )
                    }
                }
            },
            onError = { error ->
                Log.e(tag, "Failed to persist products order for listId=$listId", error)
            }
        )
    }

    private fun Product.toPresentation(): ProductUi {
        return ProductUi(
            id = id,
            name = name,
            quantity = quantity,
            unit = unit.toProductUnitOrNull(),
            isPurchased = isChecked,
            position = position,
        )
    }

    private fun String?.toProductUnitOrNull(): ProductUnit? {
        return this?.let { unitName ->
            ProductUnit.entries.firstOrNull { it.name == unitName || it.label == unitName }
        }
    }

    private fun showMenu() {
        _state.update {
            it.copy(
                activeSheet = ListDetailSheet.Menu,
                isSortSubmenuVisible = false,
            )
        }
    }

    private fun dismissMenu() {
        _state.update {
            it.copy(
                activeSheet = null,
                isSortSubmenuVisible = false,
            )
        }
    }

    private fun toggleMenu() {
        if (_state.value.activeSheet == ListDetailSheet.Menu) {
            dismissMenu()
        } else {
            showMenu()
        }
    }

    private fun toggleSortSubmenu() {
        _state.update { it.copy(isSortSubmenuVisible = !it.isSortSubmenuVisible) }
    }

    private fun setSortMode(sortMode: SortMode) {
        _state.update { state ->
            state.copy(
                sortMode = sortMode,
                products = applySorting(productsFromDb, sortMode),
                isSortSubmenuVisible = false,
                activeSheet = null,
            )
        }
    }

    private fun showDeleteAllDialog() {
        _state.update {
            it.copy(
                isDeleteAllDialogVisible = true,
                activeSheet = null,
            )
        }
    }

    private fun dismissDeleteAllDialog() {
        _state.update { it.copy(isDeleteAllDialogVisible = false) }
    }

    private fun confirmDeleteAll() {
        val listId = currentListId ?: return
        runSafely(
            block = {
                withContext(Dispatchers.IO) {
                    deleteAllProductsByListIdUseCase(listId)
                }
                _state.update { it.copy(isDeleteAllDialogVisible = false) }
            },
            onError = { error ->
                Log.e(tag, "Failed to delete all products for listId=$listId", error)
            }
        )
    }

    private fun showClearPurchasedDialog() {
        _state.update {
            it.copy(
                isClearPurchasedDialogVisible = true,
                activeSheet = null,
            )
        }
    }

    private fun dismissClearPurchasedDialog() {
        _state.update { it.copy(isClearPurchasedDialogVisible = false) }
    }

    private fun confirmClearPurchased() {
        val listId = currentListId ?: return
        runSafely(
            block = {
                withContext(Dispatchers.IO) {
                    deleteCheckedProductsByListIdUseCase(listId)
                }
                _state.update { it.copy(isClearPurchasedDialogVisible = false) }
            },
            onError = { error ->
                Log.e(tag, "Failed to clear purchased products for listId=$listId", error)
            }
        )
    }

    private fun applySorting(products: List<ProductUi>, sortMode: SortMode): ImmutableList<ProductUi> {
        return when (sortMode) {
            SortMode.ALPHABETICAL -> products.sortedBy { it.name.lowercase() }.toPersistentList()
            SortMode.CUSTOM -> products.toPersistentList()
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