package com.example.shoppinglist.features.listDetailScreen.data.repository

import com.example.shoppinglist.core.data.db.dao.ProductNameDao
import com.example.shoppinglist.core.data.db.entity.ProductNameEntity
import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductNameRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ProductNameRepositoryImpl @Inject constructor(
    private val productNameDao: ProductNameDao
) : ProductNameRepository {

    override suspend fun save(name: String) {
        productNameDao.insert(ProductNameEntity(name = name))
    }

    override suspend fun search(query: String): List<String> {
        val result = productNameDao.search(query)
        return result.getOrNull()?.map { it.name } ?: emptyList()
    }
}
