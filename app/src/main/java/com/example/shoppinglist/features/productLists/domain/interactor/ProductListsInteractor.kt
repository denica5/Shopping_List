package com.example.shoppinglist.features.productLists.domain.interactor

import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import kotlinx.coroutines.flow.Flow

interface ProductListsInteractor {
    fun getAll(): List<ProductList>
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun update(productList: ProductList)
    suspend fun create(productList: ProductList)
}