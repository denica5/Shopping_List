package com.example.shoppinglist.features.auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.features.auth.domain.useCases.LoginUseCase
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenAction
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenEvent
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    BaseViewModel<LoginScreenEvent, LoginScreenState, LoginScreenAction>(LoginScreenState()) {
    override val tag: String = "ListDetailViewModel"

    override fun obtainEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.LoginClick -> {
                login()
            }
        }
    }

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(errorMessage = null, isLoading = true) }

           loginUseCase.invoke(
                email = _state.value.email,
                password = _state.value.password
            ).onSuccess { userId ->
                _state.update { it.copy(isLoading = false) }
               Log.d("LoginViewMOdel", userId)
                _action.emit(LoginScreenAction.NavigateToProductList)
               Log.d("LoginViewMOdel", action.value.toString())
            }.onError { networkError ->
                _state.update { it.copy(errorMessage = networkError.name) }
               Log.d("LoginViewMOdel", networkError.name)
            }

        }
    }
}