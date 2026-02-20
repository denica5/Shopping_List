package com.example.shoppinglist.features.listDetailScreen.domain.usecase

import com.example.shoppinglist.features.listDetailScreen.domain.entity.Product
import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProductsByListIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    operator fun invoke(listId: Long): Flow<List<Product>> {
        return repository.getAllByListId(listId)
    }
}
