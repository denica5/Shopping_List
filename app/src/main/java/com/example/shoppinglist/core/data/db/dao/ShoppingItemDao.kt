package com.example.shoppinglist.core.data.db.dao

import android.content.ContentValues
import android.database.Cursor
import com.example.shoppinglist.core.data.db.DatabaseResult
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_ID
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_IS_BOUGHT
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_LIST_ID
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_NAME
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_POSITION
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_QUANTITY
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.COLUMN_ITEM_UNIT
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper.Companion.TABLE_SHOPPING_ITEMS
import com.example.shoppinglist.core.data.db.entity.ShoppingItemEntity
import com.example.shoppinglist.core.data.db.safeDbCall
import java.io.IOException

class ShoppingItemDao(private val dbHelper: ShoppingListDbHelper) {

  fun insert(entity: ShoppingItemEntity): DatabaseResult<Long> = safeDbCall {
    val db = dbHelper.writableDatabase
    val values = entityToContentValues(entity)
    val id = db.insert(TABLE_SHOPPING_ITEMS, null, values)
    if (id == -1L) throw IOException("Failed to insert shopping item")
    id
  }

  fun insertAll(entities: List<ShoppingItemEntity>): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      for (entity in entities) {
        val values = entityToContentValues(entity)
        val id = db.insert(TABLE_SHOPPING_ITEMS, null, values)
        if (id == -1L) throw IOException("Failed to insert shopping item: ${entity.name}")
      }
      db.setTransactionSuccessful()
    } finally {
      db.endTransaction()
    }
  }

  fun update(entity: ShoppingItemEntity): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    val values = entityToContentValues(entity)
    val rows = db.update(
      TABLE_SHOPPING_ITEMS,
      values,
      "$COLUMN_ITEM_ID = ?",
      arrayOf(entity.id.toString())
    )
    if (rows == 0) throw IOException("Failed to update shopping item with id=${entity.id}")
  }

  fun updateItem(
    itemId: Long,
    name: String,
    quantity: Double?,
    unit: String?
  ): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_ITEM_NAME, name)
      if (quantity != null) put(COLUMN_ITEM_QUANTITY, quantity) else putNull(COLUMN_ITEM_QUANTITY)
      if (unit != null) put(COLUMN_ITEM_UNIT, unit) else putNull(COLUMN_ITEM_UNIT)
    }
    val rows = db.update(
      TABLE_SHOPPING_ITEMS,
      values,
      "$COLUMN_ITEM_ID = ?",
      arrayOf(itemId.toString())
    )
    if (rows == 0) throw IOException("Failed to update item with id=$itemId")
  }

  fun updateBoughtStatus(itemId: Long, isBought: Boolean): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_ITEM_IS_BOUGHT, if (isBought) 1 else 0)
    }
    val rows = db.update(
      TABLE_SHOPPING_ITEMS,
      values,
      "$COLUMN_ITEM_ID = ?",
      arrayOf(itemId.toString())
    )
    if (rows == 0) throw IOException("Failed to update bought status for item id=$itemId")
  }

  fun updatePosition(itemId: Long, position: Int): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    val values = ContentValues().apply {
      put(COLUMN_ITEM_POSITION, position)
    }
    val rows = db.update(
      TABLE_SHOPPING_ITEMS,
      values,
      "$COLUMN_ITEM_ID = ?",
      arrayOf(itemId.toString())
    )
    if (rows == 0) throw IOException("Failed to update position for item id=$itemId")
  }

  fun deleteById(itemId: Long): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    val rows = db.delete(
      TABLE_SHOPPING_ITEMS,
      "$COLUMN_ITEM_ID = ?",
      arrayOf(itemId.toString())
    )
    if (rows == 0) throw IOException("Failed to delete item with id=$itemId")
  }

  fun deleteBoughtByListId(listId: Long): DatabaseResult<Unit> = safeDbCall {
    val db = dbHelper.writableDatabase
    db.delete(
      TABLE_SHOPPING_ITEMS,
      "$COLUMN_ITEM_LIST_ID = ? AND $COLUMN_ITEM_IS_BOUGHT = 1",
      arrayOf(listId.toString())
    )
  }

  fun getByListId(listId: Long): DatabaseResult<List<ShoppingItemEntity>> = safeDbCall {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_SHOPPING_ITEMS,
      null,
      "$COLUMN_ITEM_LIST_ID = ?",
      arrayOf(listId.toString()),
      null,
      null,
      "$COLUMN_ITEM_POSITION ASC"
    )
    cursor.use { parseListFromCursor(it) }
  }

  fun getByListIdSortedByName(listId: Long): DatabaseResult<List<ShoppingItemEntity>> = safeDbCall {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_SHOPPING_ITEMS,
      null,
      "$COLUMN_ITEM_LIST_ID = ?",
      arrayOf(listId.toString()),
      null,
      null,
      "$COLUMN_ITEM_NAME ASC"
    )
    cursor.use { parseListFromCursor(it) }
  }

  fun getById(itemId: Long): DatabaseResult<ShoppingItemEntity?> = safeDbCall {
    val db = dbHelper.readableDatabase
    val cursor = db.query(
      TABLE_SHOPPING_ITEMS,
      null,
      "$COLUMN_ITEM_ID = ?",
      arrayOf(itemId.toString()),
      null,
      null,
      null
    )
    cursor.use { c ->
      if (c.moveToFirst()) parseEntityFromCursor(c) else null
    }
  }

  private fun entityToContentValues(entity: ShoppingItemEntity): ContentValues {
    return ContentValues().apply {
      put(COLUMN_ITEM_LIST_ID, entity.listId)
      put(COLUMN_ITEM_NAME, entity.name)
      if (entity.quantity != null) {
        put(COLUMN_ITEM_QUANTITY, entity.quantity)
      } else {
        putNull(COLUMN_ITEM_QUANTITY)
      }
      if (entity.unit != null) {
        put(COLUMN_ITEM_UNIT, entity.unit)
      } else {
        putNull(COLUMN_ITEM_UNIT)
      }
      put(COLUMN_ITEM_IS_BOUGHT, if (entity.isBought) 1 else 0)
      put(COLUMN_ITEM_POSITION, entity.position)
    }
  }

  private fun parseListFromCursor(cursor: Cursor): List<ShoppingItemEntity> {
    val result = mutableListOf<ShoppingItemEntity>()
    while (cursor.moveToNext()) {
      result.add(parseEntityFromCursor(cursor))
    }
    return result
  }

  private fun parseEntityFromCursor(cursor: Cursor): ShoppingItemEntity {
    val quantityIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_QUANTITY)
    val unitIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_UNIT)
    return ShoppingItemEntity(
      id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID)),
      listId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ITEM_LIST_ID)),
      name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME)),
      quantity = if (cursor.isNull(quantityIndex)) null else cursor.getDouble(quantityIndex),
      unit = if (cursor.isNull(unitIndex)) null else cursor.getString(unitIndex),
      isBought = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_IS_BOUGHT)) == 1,
      position = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_POSITION))
    )
  }
}
