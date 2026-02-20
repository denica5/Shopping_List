package com.example.shoppinglist.features.productLists.data.repository

import com.example.shoppinglist.core.data.db.dao.ShoppingListDao
import com.example.shoppinglist.core.data.db.entity.ShoppingListEntity
import com.example.shoppinglist.core.data.db.toDomainResult
import com.example.shoppinglist.core.utils.DatabaseError
import com.example.shoppinglist.core.utils.EmptyResult
import com.example.shoppinglist.core.utils.Result
import com.example.shoppinglist.core.utils.asEmptyDataResult
import com.example.shoppinglist.core.utils.map
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ProductListsRepositoryImpl @Inject constructor(
    private val shoppingListDao: ShoppingListDao
) : ProductListsRepository {

    override fun getAll(): Result<List<ProductList>, DatabaseError> {
        return shoppingListDao.getAll()
            .toDomainResult(DatabaseError.READ_FAILED)
            .map { list ->
                list.map { ProductList(id = it.id, name = it.name, icon = null) }
            }
    }

    override suspend fun deleteById(id: Long): EmptyResult<DatabaseError> {
        return shoppingListDao.deleteById(id)
            .toDomainResult(DatabaseError.DELETE_FAILED)
            .asEmptyDataResult()
    }

    override suspend fun deleteAll() {
        val list = getAll()
        list.onSuccess { databaseList ->
            databaseList.forEach { shoppingListDao.deleteById(it.id) }
        }
            .onError { return }
    }

    override suspend fun update(productList: ProductList): EmptyResult<DatabaseError> {
        return shoppingListDao.update(
            ShoppingListEntity(
                id = productList.id,
                name = productList.name
            )
        ).toDomainResult(DatabaseError.UPDATE_FAILED)
            .asEmptyDataResult()
    }

    override suspend fun create(productList: ProductList): EmptyResult<DatabaseError> {
        return shoppingListDao.insert(
            ShoppingListEntity(
                id = productList.id,
                name = productList.name
            )
        ).toDomainResult(DatabaseError.INSERT_FAILED)
            .asEmptyDataResult()
    }
}
