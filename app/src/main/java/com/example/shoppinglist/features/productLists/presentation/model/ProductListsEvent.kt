package com.example.shoppinglist.features.productLists.presentation.model

import com.example.shoppinglist.features.productLists.domain.entity.ProductList

sealed interface ProductListsEvent {
    //class TypeSearch(val text: String) : ProductListsEvent
    object BtnCreateInClick : ProductListsEvent
    object BtnDeleteAllInClick : ProductListsEvent
    class BtnDeleteInClick(val productList: ProductList) : ProductListsEvent

    class BtnCopyInClick(val productList: ProductList) : ProductListsEvent
    class BtnEditInClick(val productList: ProductList) : ProductListsEvent
}