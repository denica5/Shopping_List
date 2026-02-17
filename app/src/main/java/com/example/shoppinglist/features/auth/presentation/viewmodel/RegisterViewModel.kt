package com.example.shoppinglist.features.auth.presentation.viewmodel

import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.core.utils.AuthError
import com.example.shoppinglist.core.utils.UIText
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.core.utils.toAuthError
import com.example.shoppinglist.core.utils.toUIText
import com.example.shoppinglist.features.auth.domain.useCases.RegisterUseCase
import com.example.shoppinglist.features.auth.presentation.model.RegisterAction
import com.example.shoppinglist.features.auth.presentation.model.RegisterEvent
import com.example.shoppinglist.features.auth.presentation.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
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
            _state.update { it.copy(passwordError = UIText.StringResource(com.example.shoppinglist.R.string.error_check_password)) }
            return
        }
        runSafely(block = {
            _state.update {
                it.copy(
                    emailError = null,
                    passwordError = null,
                    formError = null,
                    isLoading = true
                )
            }
            registerUseCase.invoke(email, password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _action.emit(RegisterAction.NavigateToLogin)
                }
                .onError { networkError ->
                    when (networkError.toAuthError()) {
                        is AuthError.Email -> _state.update { it.copy(emailError = networkError.toUIText()) }
                        is AuthError.Form -> {
                            _state.update { it.copy(formError = networkError.toUIText()) }
                            _action.emit(RegisterAction.ShowFormError(networkError.toUIText()))
                        }

                        is AuthError.Password -> _state.update { it.copy(passwordError = networkError.toUIText()) }
                    }
                    _state.update { it.copy(isLoading = false) }
                }
        }, onError = { throwable ->
            _action.emit(
                RegisterAction.ShowFormError(
                    UIText.DynamicString("Unexpected error")
                )
            )
        })
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