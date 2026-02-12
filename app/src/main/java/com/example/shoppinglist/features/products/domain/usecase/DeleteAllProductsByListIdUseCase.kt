package com.example.shoppinglist.features.products.domain.usecase

class DeleteAllProductsByListIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(listId: Int) {
        repository.deleteAllByListId(listId)
    }
}
