package com.example.shoppinglist.features.auth.data.mappers

import com.example.shoppinglist.core.utils.NetworkError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

fun mapAuthException(e: Exception): NetworkError {

    return when (e) {

        is FirebaseNetworkException ->
            NetworkError.NO_INTERNET

        is FirebaseAuthWeakPasswordException -> {
            NetworkError.WEAK_PASSWORD
        }

        is FirebaseAuthInvalidCredentialsException -> {
            when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> NetworkError.INVALID_EMAIL
                "ERROR_WRONG_PASSWORD" -> NetworkError.WRONG_PASSWORD
                "ERROR_INVALID_CREDENTIAL" -> NetworkError.INVALID_EMAIL
                else -> NetworkError.UNKNOWN
            }
        }


        is FirebaseAuthInvalidUserException -> {
            when (e.errorCode) {
                "ERROR_USER_NOT_FOUND" -> NetworkError.USER_NOT_FOUND
                "ERROR_USER_DISABLED" -> NetworkError.USER_DISABLED
                else -> NetworkError.UNKNOWN
            }
        }


        is FirebaseAuthUserCollisionException -> {
            when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> NetworkError.EMAIL_ALREADY_IN_USE
                else -> NetworkError.UNKNOWN
            }
        }


        is FirebaseAuthException -> {
            when (e.errorCode) {
                "ERROR_TOO_MANY_REQUESTS" -> NetworkError.TOO_MANY_REQUESTS
                else -> NetworkError.UNKNOWN
            }
        }

        else -> NetworkError.UNKNOWN
    }
}