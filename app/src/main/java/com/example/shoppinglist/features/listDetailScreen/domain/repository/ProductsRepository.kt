package com.example.shoppinglist.features.listDetailScreen.domain.repository

import com.example.shoppinglist.features.listDetailScreen.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getAllByListId(listId: Long): Flow<List<Product>>
    suspend fun deleteById(id: Long)
    suspend fun deleteAllByListId(listId: Long)
    suspend fun deleteCheckedByListId(listId: Long)
    suspend fun update(product: Product)
    suspend fun create(product: Product)
    suspend fun toggleChecked(id: Long)
}
