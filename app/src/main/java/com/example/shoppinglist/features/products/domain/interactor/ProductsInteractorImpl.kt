package com.example.shoppinglist.features.products.domain.interactor

import com.example.shoppinglist.features.products.domain.entity.Product
import com.example.shoppinglist.features.products.domain.repository.ProductsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class ProductsInteractorImpl @Inject constructor(
    val repository: ProductsRepository
) : ProductsInteractor {

    override fun getAllByListId(listId: Int): Flow<List<Product>> {
        return repository.getAllByListId(listId)
    }

    override suspend fun deleteById(id: Int) {
        repository.deleteById(id)
    }

    override suspend fun deleteAllByListId(listId: Int) {
        repository.deleteAllByListId(listId)
    }

    override suspend fun update(product: Product) {
        repository.update(product)
    }

    override suspend fun create(product: Product) {
        repository.create(product)
    }

    override suspend fun toggleChecked(id: Int) {
        repository.toggleChecked(id)
    }
}
