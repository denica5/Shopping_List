package com.example.shoppinglist.features.listDetailScreen.domain.usecase

import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductNameRepository
import jakarta.inject.Inject

class SaveProductNameUseCase @Inject constructor(
    private val repository: ProductNameRepository
) {
    suspend operator fun invoke(name: String) {
        if (name.isNotBlank()) {
            repository.save(name.trim())
        }
    }
}
