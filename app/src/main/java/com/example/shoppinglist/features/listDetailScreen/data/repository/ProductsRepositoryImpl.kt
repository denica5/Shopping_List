package com.example.shoppinglist.features.listDetailScreen.data.repository

import com.example.shoppinglist.features.listDetailScreen.domain.entity.Product
import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

@Singleton
class ProductsRepositoryImpl @Inject constructor() : ProductsRepository {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val products: StateFlow<List<Product>> = _products.asStateFlow()

    override fun getAllByListId(listId: Int): Flow<List<Product>> {
        return products.map { productList ->
            productList.filter { it.listId == listId }
        }
    }

    override suspend fun deleteById(id: Int) {
        _products.value = _products.value.filter { it.id != id }
    }

    override suspend fun deleteAllByListId(listId: Int) {
        _products.value = _products.value.filter { it.listId != listId }
    }

    override suspend fun update(product: Product) {
        _products.value = _products.value.map { existingProduct ->
            if (existingProduct.id == product.id) product else existingProduct
        }
    }

    override suspend fun create(product: Product) {
        _products.value += product
    }

    override suspend fun toggleChecked(id: Int) {
        _products.value = _products.value.map { product ->
            if (product.id == id) product.copy(isChecked = !product.isChecked) else product
        }
    }
}
