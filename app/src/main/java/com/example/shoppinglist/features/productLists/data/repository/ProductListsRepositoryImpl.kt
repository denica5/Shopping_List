package com.example.shoppinglist.features.productLists.data.repository

import com.example.shoppinglist.core.data.db.dao.ShoppingListDao
import com.example.shoppinglist.core.data.db.entity.ShoppingListEntity
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class ProductListsRepositoryImpl @Inject constructor(
    private val shoppingListDao: ShoppingListDao
) :
    ProductListsRepository {

    override fun getAll(): List<ProductList> {
        return shoppingListDao.getAll()
            .map { it -> ProductList(id = it.id, name = it.name, icon = null) }
    }

    override suspend fun deleteById(id: Long) {
        shoppingListDao.deleteById(id)
    }

    override suspend fun deleteAll() {

    }

    override suspend fun update(productList: ProductList) {
        shoppingListDao.update(
            ShoppingListEntity(
                id = productList.id,
                name = productList.name
            )
        )
    }

    override suspend fun create(productList: ProductList) {
        shoppingListDao.insert(
            ShoppingListEntity(
                id = productList.id,
                name = productList.name
            )
        )
    }
}
