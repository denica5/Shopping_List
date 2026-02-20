package com.example.shoppinglist.features.listDetailScreen.domain.repository

interface ProductNameRepository {
    suspend fun save(name: String)
    suspend fun search(query: String): List<String>
}
