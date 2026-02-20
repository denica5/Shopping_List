package com.example.shoppinglist.features.listDetailScreen.data.repository

import com.example.shoppinglist.core.data.db.dao.ShoppingItemDao
import com.example.shoppinglist.core.data.db.entity.ShoppingItemEntity
import com.example.shoppinglist.core.data.db.toDomainResult
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.features.listDetailScreen.domain.entity.Product
import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductsRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val shoppingItemDao: ShoppingItemDao,
) : ProductsRepository {

    private val _products = MutableStateFlow<Map<Long, List<Product>>>(emptyMap())
    private val products: StateFlow<Map<Long, List<Product>>> = _products.asStateFlow()

    override fun getAllByListId(listId: Long): Flow<List<Product>> {
        return flow {
            refreshList(listId)
            emitAll(products.map { it[listId].orEmpty() })
        }
    }

    override suspend fun deleteById(id: Long) {
        val product = shoppingItemDao.getById(id).toDomainResult()
        product.onSuccess {
            if (it != null) {
                val refresh = it.toDomain()
                shoppingItemDao.deleteById(id)
                refreshList(refresh.listId)
            } else {
                return
            }
        }.onError {
            return
        }
    }

    override suspend fun deleteAllByListId(listId: Long) {
        val existing = shoppingItemDao.getByListId(listId.toLong()).toDomainResult()

        existing.onSuccess { existing ->
            val domain = existing.map { it.toDomain() }
            domain.forEach { entity ->
                shoppingItemDao.deleteById(entity.id.toLong())
            }
            refreshList(listId)
        }
    }

    override suspend fun deleteCheckedByListId(listId: Long) {
        shoppingItemDao.deleteBoughtByListId(listId.toLong())
        refreshList(listId)
    }

    override suspend fun update(product: Product) {
        shoppingItemDao.update(product.toEntity())
        refreshList(product.listId)
    }

    override suspend fun create(product: Product) {
        shoppingItemDao.insert(product.toEntity())
        refreshList(product.listId)
    }

    override suspend fun toggleChecked(id: Long) {
        val product = shoppingItemDao.getById(id.toLong()).toDomainResult()
        product.onSuccess {
            if (it != null) {
                shoppingItemDao.updateBoughtStatus(id.toLong(), !it.isBought)
                refreshList(it.listId)
            } else {
                return
            }
        }.onError {
            return
        }
    }

    private suspend fun refreshList(listId: Long) {
        val productsForList = shoppingItemDao.getByListId(listId.toLong()).toDomainResult()
        productsForList.onSuccess { productsForListSuccess ->
            val domainProducts = productsForListSuccess.map { it.toDomain() }
            _products.value = _products.value.toMutableMap().apply {
                put(listId, domainProducts)
            }
        }.onError {
            return
        }

    }

    private fun Product.toEntity(): ShoppingItemEntity {
        return ShoppingItemEntity(
            id = id,
            listId = listId,
            name = name,
            quantity = quantity.takeIf { it > 0.0 },
            unit = unit,
            isBought = isChecked,
            position = position,
        )
    }

    private fun ShoppingItemEntity.toDomain(): Product {
        return Product(
            id = id,
            listId = listId,
            name = name,
            quantity = quantity ?: 0.0,
            unit = unit,
            isChecked = isBought,
            position = position,
        )
    }
}