package com.example.shoppinglist.features.productLists.domain.repository

import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import kotlinx.coroutines.flow.Flow

interface ProductListsRepository {
    fun getAll(): List<ProductList>
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun update(productList: ProductList)
    suspend fun create(productList: ProductList)
}