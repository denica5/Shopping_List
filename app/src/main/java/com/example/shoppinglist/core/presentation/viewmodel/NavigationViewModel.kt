package com.example.shoppinglist.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.core.domain.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(private val isLoggedInUseCase: IsLoggedInUseCase) :
    ViewModel() {
    fun isLoggedIn(): Boolean {
        return isLoggedInUseCase.invoke()
    }
}