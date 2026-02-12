package com.example.shoppinglist.features.products.domain.usecase

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.update(product)
    }
}