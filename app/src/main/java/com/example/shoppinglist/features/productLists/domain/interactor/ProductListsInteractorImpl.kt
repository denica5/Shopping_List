package com.example.shoppinglist.features.productLists.domain.interactor

import com.example.shoppinglist.core.utils.DatabaseError
import com.example.shoppinglist.core.utils.EmptyResult
import com.example.shoppinglist.core.utils.Result
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ProductListsInteractorImpl @Inject constructor(
  val repository: ProductListsRepository
) : ProductListsInteractor {

  override fun getAll(): Result<List<ProductList>, DatabaseError> {
    return repository.getAll()
  }

  override suspend fun deleteById(id: Long): EmptyResult<DatabaseError> {
    return repository.deleteById(id)
  }

  override suspend fun deleteAll() {
    repository.deleteAll()
  }

  override suspend fun update(productList: ProductList): EmptyResult<DatabaseError> {
    return repository.update(productList)
  }

  override suspend fun create(productList: ProductList): EmptyResult<DatabaseError> {
    return repository.create(productList)
  }
}