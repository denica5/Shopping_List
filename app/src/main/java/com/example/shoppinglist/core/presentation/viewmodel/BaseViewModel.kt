package com.example.shoppinglist.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event, State, Action>(initialState: State) : ViewModel() {
    abstract val tag: String

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    protected val _action = MutableSharedFlow<Action>()
    val action = _action.asSharedFlow()

    protected fun runSafely(
        block: suspend () -> Unit,
        onError: suspend (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                block()
            }.onFailure { error ->
//                if (Build.DEBUG) {
//                    Log.e(tag, "error in run safely", error)
//                } else {
//                    // save log queue
//                    // send log to server
//                }
                onError(error)
            }
        }
    }

    abstract fun obtainEvent(event: Event)
}