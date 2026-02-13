package com.example.shoppinglist.features.products.domain.usecase

import com.example.shoppinglist.features.products.domain.entity.Product
import com.example.shoppinglist.features.products.domain.repository.ProductsRepository
import jakarta.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.update(product)
    }
}
