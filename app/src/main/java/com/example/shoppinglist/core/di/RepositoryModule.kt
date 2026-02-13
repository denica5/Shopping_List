package com.example.shoppinglist.core.di

import com.example.shoppinglist.features.auth.data.AuthRepositoryImpl
import com.example.shoppinglist.features.auth.domain.AuthRepository
import com.example.shoppinglist.features.productLists.data.repository.ProductListsRepositoryImpl
import com.example.shoppinglist.features.productLists.domain.repository.ProductListsRepository
import com.example.shoppinglist.features.listDetailScreen.data.repository.ProductsRepositoryImpl
import com.example.shoppinglist.features.listDetailScreen.domain.repository.ProductsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
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

    @Binds
    @Singleton
        abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Module
    @InstallIn(SingletonComponent::class)
    object FirebaseModule {

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}
