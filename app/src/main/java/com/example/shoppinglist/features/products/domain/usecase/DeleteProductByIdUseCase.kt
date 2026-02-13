package com.example.shoppinglist.features.products.domain.usecase

import com.example.shoppinglist.features.products.domain.repository.ProductsRepository
import jakarta.inject.Inject

class DeleteProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }
}
