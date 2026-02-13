package com.example.shoppinglist.features.login.domain

import com.example.shoppinglist.features.login.domain.model.LoginRequest
import com.example.shoppinglist.features.login.domain.model.Token
import com.example.shoppinglist.features.login.domain.model.User

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): User

    suspend fun register(loginRequest: LoginRequest): User

    suspend fun refresh(): Token

    suspend fun logout()
}