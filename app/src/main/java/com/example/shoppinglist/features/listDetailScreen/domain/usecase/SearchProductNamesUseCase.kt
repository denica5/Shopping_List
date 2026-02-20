package com.example.shoppinglist.features.listDetailScreen.domain.usecase

import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductNameRepository
import jakarta.inject.Inject

class SearchProductNamesUseCase @Inject constructor(
    private val repository: ProductNameRepository
) {
    suspend operator fun invoke(query: String): List<String> {
        if (query.isBlank()) return emptyList()
        return repository.search(query.trim())
    }
}
