package com.example.shoppinglist.features.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.features.products.domain.entity.Product
import com.example.shoppinglist.features.products.domain.usecase.CreateProductUseCase
import com.example.shoppinglist.features.products.domain.usecase.DeleteAllProductsByListIdUseCase
import com.example.shoppinglist.features.products.domain.usecase.DeleteProductByIdUseCase
import com.example.shoppinglist.features.products.domain.usecase.GetProductsByListIdUseCase
import com.example.shoppinglist.features.products.domain.usecase.ToggleProductCheckedUseCase
import com.example.shoppinglist.features.products.domain.usecase.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import jakarta.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsByListId: GetProductsByListIdUseCase,
    private val createProduct: CreateProductUseCase,
    private val updateProduct: UpdateProductUseCase,
    private val deleteProductById: DeleteProductByIdUseCase,
    private val deleteAllProducts: DeleteAllProductsByListIdUseCase,
    private val toggleProductChecked: ToggleProductCheckedUseCase
) : ViewModel() {

    fun getProducts(listId: Int) =
        getProductsByListId(listId)

    fun create(product: Product) = viewModelScope.launch {
        createProduct(product)
    }

    fun update(product: Product) = viewModelScope.launch {
        updateProduct(product)
    }

    fun delete(id: Int) = viewModelScope.launch {
        deleteProductById(id)
    }

    fun deleteAll(listId: Int) = viewModelScope.launch {
        deleteAllProducts(listId)
    }

    fun toggleChecked(id: Int) = viewModelScope.launch {
        toggleProductChecked(id)
    }
}
