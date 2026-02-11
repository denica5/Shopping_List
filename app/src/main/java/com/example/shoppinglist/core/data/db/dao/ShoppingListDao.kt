package com.example.shoppinglist.core.data.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_LIST_CREATED_AT
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_LIST_ID
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_LIST_NAME
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.TABLE_SHOPPING_LISTS
import com.example.shoppinglist.core.data.db.entity.ShoppingListEntity
import java.io.IOException

class ShoppingListDao(private val dbHelper: ShoppingListDbHelper) {

  fun insert(entity: ShoppingListEntity): Long {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_LIST_NAME, entity.name)
      put(COLUMN_LIST_CREATED_AT, entity.createdAt)
    }
    val id = db.insert(TABLE_SHOPPING_LISTS, null, values)
    if (id == -1L) throw IOException("Failed to insert shopping list")
    return id
  }

  fun update(entity: ShoppingListEntity) {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_LIST_NAME, entity.name)
      put(COLUMN_LIST_CREATED_AT, entity.createdAt)
    }
    val rows = db.update(
      TABLE_SHOPPING_LISTS,
      values,
      "$COLUMN_LIST_ID = ?",
      arrayOf(entity.id.toString())
    )
    if (rows == 0) throw IOException("Failed to update shopping list with id=${entity.id}")
  }

  fun rename(listId: Long, name: String) {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_LIST_NAME, name)
    }
    val rows = db.update(
      TABLE_SHOPPING_LISTS,
      values,
      "$COLUMN_LIST_ID = ?",
      arrayOf(listId.toString())
    )
    if (rows == 0) throw IOException("Failed to rename shopping list with id=$listId")
  }

  fun deleteById(listId: Long) {
    val db = dbHelper.writableDatabase
    val rows = db.delete(
      TABLE_SHOPPING_LISTS,
      "$COLUMN_LIST_ID = ?",
      arrayOf(listId.toString())
    )
    if (rows == 0) throw IOException("Failed to delete shopping list with id=$listId")
  }

  fun getAll(): List<ShoppingListEntity> {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_SHOPPING_LISTS,
      null,
      null,
      null,
      null,
      null,
      "$COLUMN_LIST_CREATED_AT DESC"
    )
    return cursor.use { parseListFromCursor(it) }
  }

  fun getById(listId: Long): ShoppingListEntity? {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_SHOPPING_LISTS,
      null,
      "$COLUMN_LIST_ID = ?",
      arrayOf(listId.toString()),
      null,
      null,
      null
    )
    return cursor.use { c ->
      if (c.moveToFirst()) parseEntityFromCursor(c) else null
    }
  }

  private fun parseListFromCursor(cursor: Cursor): List<ShoppingListEntity> {
    val result = mutableListOf<ShoppingListEntity>()
    while (cursor.moveToNext()) {
      result.add(parseEntityFromCursor(cursor))
    }
    return result
  }

  private fun parseEntityFromCursor(cursor: Cursor): ShoppingListEntity {
    return ShoppingListEntity(
      id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LIST_ID)),
      name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIST_NAME)),
      createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LIST_CREATED_AT))
    )
  }
}
