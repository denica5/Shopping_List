package com.example.shoppinglist.features.productLists.data.repository

import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class ProductListsRepositoryImpl @Inject constructor() : ProductListsRepository {

    private val _productLists = MutableStateFlow<List<ProductList>>(emptyList())
    private val productLists: StateFlow<List<ProductList>> = _productLists.asStateFlow()


    override fun getAll(): Flow<List<ProductList>> {
        return productLists
    }

    override suspend fun deleteById(id: Int) {
        _productLists.value = _productLists.value.filter { it.id != id }
    }

    override suspend fun deleteAll() {
        _productLists.value = emptyList()
    }

    override suspend fun update(productList: ProductList) {
        _productLists.value = _productLists.value.map { existingList ->
            if (existingList.id == productList.id) productList else existingList
        }
    }

    override suspend fun create(productList: ProductList) {
        _productLists.value += productList
    }
}
