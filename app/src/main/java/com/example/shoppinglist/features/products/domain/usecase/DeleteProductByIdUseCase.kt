package com.example.shoppinglist.features.products.domain.usecase

class DeleteProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteById(id)
    }
}
