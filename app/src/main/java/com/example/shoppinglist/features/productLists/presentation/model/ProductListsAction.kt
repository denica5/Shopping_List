package com.example.shoppinglist.features.productLists.presentation.model

import com.example.shoppinglist.features.productLists.domain.entity.ProductList

sealed interface ProductListsAction {

    object ShowNone : ProductListsAction
    class ShowCreateDialog(
        val onPosBtnClick: (name:String,icon: String?) -> Unit,
        val onCancelBtnClick: () -> Unit
    ) : ProductListsAction

    class ShowDeleteDialog(
        val productList : ProductList,
        val onPosBtnClick: (id:Long) -> Unit,
        val onCancelBtnClick: () -> Unit
    ) : ProductListsAction

    class ShowDeleteAllDialog(
        val onPosBtnClick: () -> Unit,
        val onCancelBtnClick: () -> Unit
    ) : ProductListsAction

    class ShowEditDialog(
        val productList : ProductList,
        val onPosBtnClick: (productList : ProductList) -> Unit,
        val onCancelBtnClick: () -> Unit
    ) : ProductListsAction
}