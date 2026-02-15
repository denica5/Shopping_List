package com.example.shoppinglist.features.auth.presentation.model

sealed interface LoginScreenEvent {
    object LoginClick : LoginScreenEvent
}