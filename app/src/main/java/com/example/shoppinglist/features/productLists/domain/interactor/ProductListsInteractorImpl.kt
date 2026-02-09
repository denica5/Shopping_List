package com.example.shoppinglist.features.productLists.domain.interactor

import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class ProductListsInteractorImpl @Inject constructor(val repository: ProductListsRepository) :
    ProductListsInteractor {
    override fun getAll(): Flow<List<ProductList>> {
        return repository.getAll()
    }

    override suspend fun deleteById(id: Int) {
        repository.deleteById(id)
    }

    override suspend fun deleteAll() {
        repository.deleteAll()
    }

    override suspend fun update(productList: ProductList) {
        repository.update(productList)
    }

    override suspend fun create(productList: ProductList) {
        repository.create(productList)
    }
}