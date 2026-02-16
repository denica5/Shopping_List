package com.example.shoppinglist.features.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.features.auth.domain.useCases.RegisterUseCase
import com.example.shoppinglist.features.auth.presentation.model.RegisterAction
import com.example.shoppinglist.features.auth.presentation.model.RegisterEvent
import com.example.shoppinglist.features.auth.presentation.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val registerUseCase: RegisterUseCase) :
    BaseViewModel<RegisterEvent, RegisterState, RegisterAction>(
        RegisterState()
    ) {
    override val tag: String = "RegisterViewModel"
    override fun obtainEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.RegisterClick -> {
                register(state.value.email, state.value.password, state.value.passwordCheck)
            }
        }
    }

    fun register(email: String, password: String, passwordCheck: String) {
        if (password != passwordCheck) {
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(errorMessage = null, isLoading = true) }
            registerUseCase.invoke(email, password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _action.emit(RegisterAction.NavigateToLogin)
                }
                .onError { networkError ->
                    _state.update {
                        it.copy(errorMessage = networkError.name, isLoading = false)
                    }
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

    fun onPasswordCheckChange(passwordCheck: String) {
        _state.update {
            it.copy(passwordCheck = passwordCheck)
        }
    }

}