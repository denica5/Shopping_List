package com.example.shoppinglist.features.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.core.presentation.viewmodel.BaseViewModel
import com.example.shoppinglist.core.utils.onError
import com.example.shoppinglist.core.utils.onSuccess
import com.example.shoppinglist.features.auth.domain.useCases.ResetPasswordUseCase
import com.example.shoppinglist.features.auth.presentation.model.ResetPasswordAction
import com.example.shoppinglist.features.auth.presentation.model.ResetPasswordEvent
import com.example.shoppinglist.features.auth.presentation.model.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            resetPasswordUseCase.invoke(email)
                .onSuccess {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _action.emit(ResetPasswordAction.NavigateToLogin)

                }.onError { networkError ->
                    _state.update { it.copy(isLoading = false, errorMessage = networkError.name) }

                }
        }
    }

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(email = email)
        }
    }


}


