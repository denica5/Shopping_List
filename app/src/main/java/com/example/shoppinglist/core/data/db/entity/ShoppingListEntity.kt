package com.example.shoppinglist.core.data.db.entity

data class ShoppingListEntity(
  val id: Long = 0,
  val name: String,
  val createdAt: Long = System.currentTimeMillis()
)
