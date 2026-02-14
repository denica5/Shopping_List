package com.example.shoppinglist.core.data.db.entity

data class ShoppingItemEntity(
  val id: Long = 0,
  val listId: Long,
  val name: String,
  val quantity: Double? = null,
  val unit: String? = null,
  val isBought: Boolean = false,
  val position: Int = 0
)
