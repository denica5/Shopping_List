package com.example.shoppinglist.features.auth.presentation.viewmodel

import android.util.Log
import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.core.utils.AuthError
import com.example.shoppinglist.core.utils.UIText
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.core.utils.toAuthError
import com.example.shoppinglist.core.utils.toUIText
import com.example.shoppinglist.features.auth.domain.useCases.LoginUseCase
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenAction
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenEvent
import com.example.shoppinglist.features.auth.presentation.model.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
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
        runSafely(
            block = {
                _state.update {
                    it.copy(
                        emailError = null,
                        passwordError = null,
                        formError = null,
                        isLoading = true
                    )
                }

                loginUseCase.invoke(
                    email = _state.value.email,
                    password = _state.value.password
                ).onSuccess { userId ->
                    _state.update { it.copy(isLoading = false) }
                    Log.d(tag, userId)
                    _action.emit(LoginScreenAction.NavigateToProductList)
                    Log.d(tag, action.replayCache.toString())
                }.onError { networkError ->
                    when (networkError.toAuthError()) {
                        is AuthError.Email -> _state.update { it.copy(emailError = networkError.toUIText()) }
                        is AuthError.Form -> {
                            _state.update { it.copy(formError = networkError.toUIText()) }
                            _action.emit(LoginScreenAction.ShowFormError(networkError.toUIText()))
                        }

                        is AuthError.Password -> _state.update { it.copy(passwordError = networkError.toUIText()) }
                    }
                    _state.update { it.copy(isLoading = false) }

                    Log.d(tag, networkError.name)
                    Log.d(tag, _action.replayCache.toString())
                }

            },
            onError = { throwable ->
                _action.emit(
                    LoginScreenAction.ShowFormError(
                        UIText.DynamicString("Unexpected error")
                    )
                )
            })
    }
}