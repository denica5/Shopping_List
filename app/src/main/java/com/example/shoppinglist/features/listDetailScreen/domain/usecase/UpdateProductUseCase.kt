package com.example.shoppinglist.features.listDetailScreen.domain.usecase

import com.example.shoppinglist.features.listDetailScreen.domain.entity.Product
import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductsRepository
import jakarta.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.update(product)
    }
}
