package com.example.shoppinglist.core.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ShoppingListDbHelper(context: Context) : SQLiteOpenHelper(
  context,
  DATABASE_NAME,
  null,
  DATABASE_VERSION
) {

  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(CREATE_SHOPPING_LISTS_TABLE)
    db.execSQL(CREATE_SHOPPING_ITEMS_TABLE)
    db.execSQL(CREATE_PRODUCT_NAMES_TABLE)
    db.execSQL(CREATE_ITEMS_LIST_ID_INDEX)
    db.execSQL(CREATE_PRODUCT_NAMES_NAME_INDEX)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("DROP TABLE IF EXISTS $TABLE_SHOPPING_ITEMS")
    db.execSQL("DROP TABLE IF EXISTS $TABLE_SHOPPING_LISTS")
    db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT_NAMES")
    onCreate(db)
  }

  override fun onConfigure(db: SQLiteDatabase) {
    super.onConfigure(db)
    db.setForeignKeyConstraintsEnabled(true)
  }

  companion object {
    const val DATABASE_NAME = "shopping_list.db"
    const val DATABASE_VERSION = 1

    const val TABLE_SHOPPING_LISTS = "shopping_lists"
    const val TABLE_SHOPPING_ITEMS = "shopping_items"
    const val TABLE_PRODUCT_NAMES = "product_names"

    // shopping_lists columns
    const val COLUMN_LIST_ID = "id"
    const val COLUMN_LIST_NAME = "name"
    const val COLUMN_LIST_CREATED_AT = "created_at"

    // shopping_items columns
    const val COLUMN_ITEM_ID = "id"
    const val COLUMN_ITEM_LIST_ID = "list_id"
    const val COLUMN_ITEM_NAME = "name"
    const val COLUMN_ITEM_QUANTITY = "quantity"
    const val COLUMN_ITEM_UNIT = "unit"
    const val COLUMN_ITEM_IS_BOUGHT = "is_bought"
    const val COLUMN_ITEM_POSITION = "position"

    // product_names columns
    const val COLUMN_PRODUCT_ID = "id"
    const val COLUMN_PRODUCT_NAME = "name"

    private const val CREATE_SHOPPING_LISTS_TABLE = """
      CREATE TABLE $TABLE_SHOPPING_LISTS (
        $COLUMN_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_LIST_NAME TEXT NOT NULL,
        $COLUMN_LIST_CREATED_AT INTEGER NOT NULL DEFAULT 0
      )
    """

    private const val CREATE_SHOPPING_ITEMS_TABLE = """
      CREATE TABLE $TABLE_SHOPPING_ITEMS (
        $COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_ITEM_LIST_ID INTEGER NOT NULL,
        $COLUMN_ITEM_NAME TEXT NOT NULL,
        $COLUMN_ITEM_QUANTITY REAL,
        $COLUMN_ITEM_UNIT TEXT,
        $COLUMN_ITEM_IS_BOUGHT INTEGER NOT NULL DEFAULT 0,
        $COLUMN_ITEM_POSITION INTEGER NOT NULL DEFAULT 0,
        FOREIGN KEY ($COLUMN_ITEM_LIST_ID)
          REFERENCES $TABLE_SHOPPING_LISTS($COLUMN_LIST_ID)
          ON DELETE CASCADE
      )
    """

    private const val CREATE_PRODUCT_NAMES_TABLE = """
      CREATE TABLE $TABLE_PRODUCT_NAMES (
        $COLUMN_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_PRODUCT_NAME TEXT NOT NULL UNIQUE
      )
    """

    private const val CREATE_ITEMS_LIST_ID_INDEX =
      "CREATE INDEX idx_items_list_id ON $TABLE_SHOPPING_ITEMS($COLUMN_ITEM_LIST_ID)"

    private const val CREATE_PRODUCT_NAMES_NAME_INDEX =
      "CREATE INDEX idx_product_names_name ON $TABLE_PRODUCT_NAMES($COLUMN_PRODUCT_NAME)"
  }
}
