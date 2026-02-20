package com.example.shoppinglist.features.listDetailScreen.domain.usecase

import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductsRepository
import jakarta.inject.Inject

class DeleteProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteById(id)
    }
}
