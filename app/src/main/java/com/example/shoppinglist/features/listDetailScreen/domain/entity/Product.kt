package com.example.shoppinglist.features.listDetailScreen.domain.entity

data class Product(
    val id: Long = 0,
    val listId: Long,
    val name: String,
    val quantity: Double = 0.0,
    val unit: String? = null,
    val isChecked: Boolean = false,
    val position: Int = 0,
)
