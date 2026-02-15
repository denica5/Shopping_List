package com.example.shoppinglist.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.features.auth.domain.useCases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val registerUseCase: RegisterUseCase) : ViewModel() {
    fun register(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { registerUseCase.invoke(email, password) }
        }
    }

}