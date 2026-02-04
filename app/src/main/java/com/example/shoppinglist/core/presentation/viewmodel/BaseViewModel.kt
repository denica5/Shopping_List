package com.example.shoppinglist.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event, State, Action>(initialState: State) : ViewModel() {
    abstract val tag: String

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    protected val _action = MutableStateFlow<Action?>(null)
    val action = _action.asStateFlow()

    protected fun runSafely(
        block: suspend () -> Unit,
        onError: suspend (Throwable) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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

    open fun obtainEvent(event: Event) {}
}