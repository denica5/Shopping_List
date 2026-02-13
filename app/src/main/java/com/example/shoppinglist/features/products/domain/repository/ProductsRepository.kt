package com.example.shoppinglist.features.products.domain.repository

import com.example.shoppinglist.features.products.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getAllByListId(listId: Int): Flow<List<Product>>
    suspend fun deleteById(id: Int)
    suspend fun deleteAllByListId(listId: Int)
    suspend fun update(product: Product)
    suspend fun create(product: Product)
    suspend fun toggleChecked(id: Int)
}
