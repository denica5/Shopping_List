package com.example.shoppinglist.features.auth.presentation.viewmodel

import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.core.utils.AuthError
import com.example.shoppinglist.core.utils.UIText
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.core.utils.toAuthError
import com.example.shoppinglist.core.utils.toUIText
import com.example.shoppinglist.features.auth.domain.useCases.ResetPasswordUseCase
import com.example.shoppinglist.features.auth.presentation.model.ResetPasswordAction
import com.example.shoppinglist.features.auth.presentation.model.ResetPasswordEvent
import com.example.shoppinglist.features.auth.presentation.model.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val resetPasswordUseCase: ResetPasswordUseCase) :
    BaseViewModel<ResetPasswordEvent, ResetPasswordState, ResetPasswordAction>(
        ResetPasswordState()
    ) {
    override val tag: String = "ResetPasswordViewModel"
    override fun obtainEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.ResetPasswordClick -> {
                resetPassword(state.value.email)
            }
        }
    }

    fun resetPassword(email: String) {
        runSafely({
            _state.update {
                it.copy(
                    emailError = null,
                    formError = null,
                    isLoading = true
                )
            }
            resetPasswordUseCase.invoke(email)
                .onSuccess {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _action.emit(ResetPasswordAction.NavigateToLogin)

                }.onError { networkError ->
                    when (networkError.toAuthError()) {
                        is AuthError.Email -> _state.update { it.copy(emailError = networkError.toUIText()) }
                        is AuthError.Form -> {
                            _state.update { it.copy(formError = networkError.toUIText()) }
                            _action.emit(ResetPasswordAction.ShowFormError(networkError.toUIText()))
                        }

                        else -> _state.update { it.copy(formError = networkError.toUIText()) }
                    }
                    _state.update { it.copy(isLoading = false) }

                }
        }, onError = { throwable ->
            _action.emit(
                ResetPasswordAction.ShowFormError(
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


}


