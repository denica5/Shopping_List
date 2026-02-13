package com.example.shoppinglist.features.listDetailScreen.domain.entity

data class Product(
    val id: Int,
    val listId: Int,
    val name: String,
    val quantity: String? = null,
    val isChecked: Boolean = false
)
