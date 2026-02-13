package com.example.shoppinglist.core.di

import com.example.shoppinglist.features.productLists.domain.interactor.ProductListsInteractor
import com.example.shoppinglist.features.productLists.domain.interactor.ProductListsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    @Binds
    @Singleton
    abstract fun bindProductListsInteractor(
        impl: ProductListsInteractorImpl
    ): ProductListsInteractor
}
