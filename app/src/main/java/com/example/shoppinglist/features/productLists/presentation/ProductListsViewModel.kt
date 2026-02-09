package com.example.shoppinglist.features.productLists.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.interactor.ProductListsInteractor
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsAction
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsEvent
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListsViewModel @Inject constructor(val interactor: ProductListsInteractor) :
    ViewModel() {

    var id = 1
    private val _state = MutableStateFlow(ProductListsState(mutableListOf()))
    val state = _state.asStateFlow()
    private val _action = MutableStateFlow<ProductListsAction?>(null)
    val action = _action.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.getAll()
                .collect { lists ->
                    _state.update {
                        it.copy(productLists = lists)
                    }
                }
        }
    }

    fun obtainEvent(event: ProductListsEvent) {
        when (event) {
            is ProductListsEvent.BtnCopyInClick -> {
                viewModelScope.launch {
                    clearAction()
                    id += 1
                    interactor.create(event.productList.copy(id = id))
                }
            }

            is ProductListsEvent.BtnEditInClick -> {
                _action.update {
                    ProductListsAction.ShowEditDialog(
                        onPosBtnClick = { list ->
                            viewModelScope.launch {
                                clearAction()
                                interactor.update(list)
                            }
                        },
                        onCancelBtnClick = { clearAction() },
                        productList = event.productList
                    )
                }
            }

            is ProductListsEvent.BtnDeleteAllInClick -> {
                _action.update {
                    ProductListsAction.ShowDeleteAllDialog(
                        onPosBtnClick = {
                            viewModelScope.launch {
                                clearAction()
                                interactor.deleteAll()
                            }
                        },
                        onCancelBtnClick = { clearAction() }
                    )
                }
            }

            is ProductListsEvent.BtnCreateInClick -> {
                _action.update {
                    ProductListsAction.ShowCreateDialog(
                        onPosBtnClick = { name, icon ->
                            clearAction()
                            id += 1
                            viewModelScope.launch {
                                interactor.create(ProductList(name = name, icon = icon, id = id))
                            }
                        },
                        onCancelBtnClick = {
                            clearAction()
                        })
                }
            }

            is ProductListsEvent.BtnDeleteInClick -> {
                _action.update {
                    ProductListsAction.ShowDeleteDialog(
                        onPosBtnClick = { id ->
                            clearAction()

                            viewModelScope.launch {
                                interactor.deleteById(id)
                            }
                        },
                        onCancelBtnClick = {
                            clearAction()
                        }, productList = event.productList
                    )
                }
            }

        }
    }

    private fun runSafely(
        block: suspend () -> Unit,
        onError: suspend (Throwable) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                block()
            }.onFailure { error ->
                Log.e("tag", "error in run safely", error)

                onError(error)
            }
        }
    }

    private fun clearAction() {
        _action.update { null }
    }
}


