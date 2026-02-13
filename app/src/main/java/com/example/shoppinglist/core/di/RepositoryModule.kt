package com.example.shoppinglist.core.di

import com.example.shoppinglist.features.productLists.data.repository.ProductListsRepositoryImpl
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import com.example.shoppinglist.features.products.data.repository.ProductsRepositoryImpl
import com.example.shoppinglist.features.products.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductListsRepository(
        impl: ProductListsRepositoryImpl
    ): ProductListsRepository

    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        impl: ProductsRepositoryImpl
    ): ProductsRepository
}
