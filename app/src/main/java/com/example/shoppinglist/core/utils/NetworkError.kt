package com.example.shoppinglist.core.utils

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
    EMAIL_ALREADY_IN_USE
}