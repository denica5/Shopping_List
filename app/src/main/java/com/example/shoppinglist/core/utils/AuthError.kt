package com.example.shoppinglist.core.utils

sealed class AuthError {

    data class Email(val message: UIText) : AuthError()
    data class Password(val message: UIText) : AuthError()
    data class Form(val message: UIText) : AuthError()
}