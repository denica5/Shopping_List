package com.example.shoppinglist.features.products.domain.usecase

class CreateProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.create(product)
    }
}
