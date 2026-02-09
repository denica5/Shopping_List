package com.example.shoppinglist.features.productLists.domain.repository

import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import kotlinx.coroutines.flow.Flow

interface ProductListsRepository {
    fun getAll(): Flow<List<ProductList>>
    suspend fun deleteById(id: Int)
    suspend fun deleteAll()
    suspend fun update(productList: ProductList)
    suspend fun create(productList: ProductList)
}