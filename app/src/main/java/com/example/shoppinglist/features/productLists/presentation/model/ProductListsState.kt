package com.example.shoppinglist.features.productLists.presentation.model

import com.example.shoppinglist.features.productLists.domain.entity.ProductList

data class ProductListsState(
    val productLists: List<ProductList>
)