package com.example.shoppinglist.features.auth.domain

import com.example.shoppinglist.core.utils.NetworkError
import com.example.shoppinglist.core.utils.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<String, NetworkError>
    fun logout()
    fun isLoggedIn(): Boolean
    suspend fun resetPassword(email: String): Result<Unit, NetworkError>

    suspend fun register(email: String, password: String): Result<String, NetworkError>
}