package com.example.shoppinglist.features.productLists.domain.interactor

import com.example.shoppinglist.core.utils.DatabaseError
import com.example.shoppinglist.core.utils.EmptyResult
import com.example.shoppinglist.core.utils.Result
import com.example.shoppinglist.features.productLists.domain.entity.ProductList

interface ProductListsInteractor {
  fun getAll(): Result<List<ProductList>, DatabaseError>
  suspend fun deleteById(id: Long): EmptyResult<DatabaseError>
  suspend fun deleteAll()
  suspend fun update(productList: ProductList): EmptyResult<DatabaseError>
  suspend fun create(productList: ProductList): EmptyResult<DatabaseError>
}