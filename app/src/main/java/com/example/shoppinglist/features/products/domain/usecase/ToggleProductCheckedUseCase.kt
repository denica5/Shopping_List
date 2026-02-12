package com.example.shoppinglist.features.products.domain.usecase

class ToggleProductCheckedUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.toggleChecked(id)
    }
}
