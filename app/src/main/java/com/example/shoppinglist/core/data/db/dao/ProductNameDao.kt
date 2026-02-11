package com.example.shoppinglist.core.data.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_PRODUCT_ID
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_PRODUCT_NAME
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.TABLE_PRODUCT_NAMES
import com.example.shoppinglist.core.data.db.entity.ProductNameEntity
import java.io.IOException

class ProductNameDao(private val dbHelper: ShoppingListDbHelper) {

  fun insert(entity: ProductNameEntity): Long {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_PRODUCT_NAME, entity.name)
    }
    val id = db.insertWithOnConflict(
      TABLE_PRODUCT_NAMES,
      null,
      values,
      android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE
    )
    if (id == -1L) throw IOException("Failed to insert product name")
    return id
  }

  fun search(query: String): List<ProductNameEntity> {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_PRODUCT_NAMES,
      null,
      "$COLUMN_PRODUCT_NAME LIKE ?",
      arrayOf("$query%"),
      null,
      null,
      "$COLUMN_PRODUCT_NAME ASC"
    )
    return cursor.use { parseListFromCursor(it) }
  }

  fun getAll(): List<ProductNameEntity> {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_PRODUCT_NAMES,
      null,
      null,
      null,
      null,
      null,
      "$COLUMN_PRODUCT_NAME ASC"
    )
    return cursor.use { parseListFromCursor(it) }
  }

  private fun parseListFromCursor(cursor: Cursor): List<ProductNameEntity> {
    val result = mutableListOf<ProductNameEntity>()
    while (cursor.moveToNext()) {
      result.add(parseEntityFromCursor(cursor))
    }
    return result
  }

  private fun parseEntityFromCursor(cursor: Cursor): ProductNameEntity {
    return ProductNameEntity(
      id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID)),
      name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME))
    )
  }
}
