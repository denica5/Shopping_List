package com.example.shoppinglist.features.products.domain.usecase

import com.example.shoppinglist.features.products.domain.entity.Product
import com.example.shoppinglist.features.products.domain.repository.ProductsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProductsByListIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    operator fun invoke(listId: Int): Flow<List<Product>> {
        return repository.getAllByListId(listId)
    }
}
