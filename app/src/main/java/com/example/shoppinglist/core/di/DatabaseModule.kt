package com.example.shoppinglist.core.di

import android.content.Context
import com.example.shoppinglist.core.data.db.ShoppingListDbHelper
import com.example.shoppinglist.core.data.db.dao.ProductNameDao
import com.example.shoppinglist.core.data.db.dao.ShoppingItemDao
import com.example.shoppinglist.core.data.db.dao.ShoppingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideDbHelper(@ApplicationContext context: Context): ShoppingListDbHelper {
    return ShoppingListDbHelper(context)
  }

  @Provides
  @Singleton
  fun provideShoppingListDao(dbHelper: ShoppingListDbHelper): ShoppingListDao {
    return ShoppingListDao(dbHelper)
  }

  @Provides
  @Singleton
  fun provideShoppingItemDao(dbHelper: ShoppingListDbHelper): ShoppingItemDao {
    return ShoppingItemDao(dbHelper)
  }

  @Provides
  @Singleton
  fun provideProductNameDao(dbHelper: ShoppingListDbHelper): ProductNameDao {
    return ProductNameDao(dbHelper)
  }
}
