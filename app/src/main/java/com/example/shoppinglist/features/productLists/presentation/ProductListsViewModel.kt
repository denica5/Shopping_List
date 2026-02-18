package com.example.shoppinglist.features.productLists.presentation

import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.interactor.ProductListsInteractor
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsAction
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsEvent
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ProductListsViewModel @Inject constructor(
    val interactor: ProductListsInteractor,
) :
    BaseViewModel<ProductListsEvent, ProductListsState, ProductListsAction>(
        ProductListsState(
            mutableListOf()
        )
    ) {
    override val tag: String = "ProductListsViewModel"
    private val _actionDialog = MutableStateFlow<ProductListsAction?>(null)
    val actionDialog = _actionDialog.asStateFlow()


    init {
        updateList()
    }


    private fun updateList() {
        runSafely(block = {
            _state.update {
                it.copy(productLists = interactor.getAll())
            }
        }, onError = {})
    }


    override fun obtainEvent(event: ProductListsEvent) {
        when (event) {
            is ProductListsEvent.BtnCopyInClick -> {
                runSafely(block = {
                    clearAction()
                    interactor.create(event.productList)
                    updateList()
                }, onError = {})
            }

            is ProductListsEvent.BtnEditInClick -> {
                _actionDialog.update {
                    ProductListsAction.ShowEditDialog(
                        onPosBtnClick = { list ->
                            runSafely(block = {
                                clearAction()
                                interactor.update(list)
                                updateList()
                            }, onError = {})

                        },
                        onCancelBtnClick = { clearAction() },
                        productList = event.productList
                    )
                }
            }

            is ProductListsEvent.BtnDeleteAllInClick -> {
                _actionDialog.update {
                    ProductListsAction.ShowDeleteAllDialog(
                        onPosBtnClick = {
                            runSafely(block = {
                                clearAction()
                                interactor.deleteAll()
                                updateList()
                            }, onError = {})

                        },
                        onCancelBtnClick = { clearAction() }
                    )
                }
            }

            is ProductListsEvent.BtnCreateInClick -> {
                _actionDialog.update {
                    ProductListsAction.ShowCreateDialog(
                        onPosBtnClick = { name, icon ->
                            clearAction()

                            runSafely(block = {
                                interactor.create(ProductList(name = name, icon = icon))
                                updateList()
                            }, onError = {})


                        },
                        onCancelBtnClick = {
                            clearAction()
                        })
                }
            }

            is ProductListsEvent.BtnDeleteInClick -> {
                _actionDialog.update {
                    ProductListsAction.ShowDeleteDialog(
                        onPosBtnClick = { id ->
                            clearAction()

                            runSafely(block = {
                                interactor.deleteById(id)
                                updateList()
                            }, onError = {})

                        },
                        onCancelBtnClick = {
                            clearAction()
                        }, productList = event.productList
                    )
                }
            }

        }
    }

    private fun clearAction() {
        _actionDialog.update { null }
    }
}


