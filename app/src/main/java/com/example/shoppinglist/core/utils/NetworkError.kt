package com.example.shoppinglist.core.utils

import com.example.shoppinglist.R
import com.example.shoppinglist.core.utils.UIText.*

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    WEAK_PASSWORD,
    UNKNOWN,
    WRONG_PASSWORD,
    USER_NOT_FOUND,
    INVALID_EMAIL,
    USER_DISABLED,
    EMAIL_ALREADY_IN_USE,
    INVALID_CREDENTIAL
}


fun NetworkError.toUIText(): UIText {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT ->
            StringResource(R.string.network_error_request_timeout)

        NetworkError.UNAUTHORIZED -> StringResource(R.string.network_error_unknown)
        NetworkError.CONFLICT -> StringResource(R.string.network_error_unknown)
        NetworkError.TOO_MANY_REQUESTS -> StringResource(R.string.network_error_unknown)
        NetworkError.NO_INTERNET -> StringResource(R.string.network_error_no_internet)
        NetworkError.PAYLOAD_TOO_LARGE -> StringResource(R.string.network_error_unknown)
        NetworkError.WEAK_PASSWORD -> StringResource(R.string.network_error_weak_password)
        NetworkError.UNKNOWN -> StringResource(R.string.network_error_unknown)
        NetworkError.WRONG_PASSWORD -> StringResource(R.string.network_error_invalid_credential)
        NetworkError.USER_NOT_FOUND -> StringResource(R.string.network_error_user_not_found)
        NetworkError.INVALID_EMAIL -> StringResource(R.string.network_error_invalid_credential)
        NetworkError.USER_DISABLED -> StringResource(R.string.network_error_unknown)
        NetworkError.EMAIL_ALREADY_IN_USE -> StringResource(R.string.network_error_email_already_in_use)
        NetworkError.INVALID_CREDENTIAL -> StringResource(R.string.network_error_invalid_credential)
    }
}

fun NetworkError.toAuthError(): AuthError {

    val message = this.toUIText()

    return when (this) {

        NetworkError.INVALID_EMAIL,
        NetworkError.USER_NOT_FOUND,
        NetworkError.EMAIL_ALREADY_IN_USE -> {
            AuthError.Email(message)
        }

        NetworkError.WRONG_PASSWORD,
        NetworkError.WEAK_PASSWORD -> {
            AuthError.Password(message)
        }

        else -> {
            AuthError.Form(message)
        }
    }
}