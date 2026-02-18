package com.example.shoppinglist.features.listDetailScreen.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProductUi(
    val id: Int,
    val name: String,
    val quantity: Double,
    val unit: ProductUnit? = null,
    val isPurchased: Boolean = false,
    val position: Int = 0,
)