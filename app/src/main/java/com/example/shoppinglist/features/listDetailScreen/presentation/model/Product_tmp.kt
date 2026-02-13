package com.example.shoppinglist.features.listDetailScreen.presentation.model

// TODO: временный data class для тестов, удалить заменить на доменную сущность по готовности БД
data class Product_tmp(
    val id: Int,
    val name: String,
    val quantity: Double,
    val unit: ProductUnit? = null,
    val isPurchased: Boolean = false,
)