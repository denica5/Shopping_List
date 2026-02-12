package com.example.shoppinglist.features.products.domain.usecase

class GetProductsByListIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    operator fun invoke(listId: Int): Flow<List<Product>> {
        return repository.getAllByListId(listId)
    }
}
