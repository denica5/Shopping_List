package com.example.shoppinglist.features.auth.data

import com.example.shoppinglist.features.auth.data.dto.CheckTokenDto
import com.example.shoppinglist.features.auth.data.dto.LoginRequestDto
import com.example.shoppinglist.features.auth.data.dto.TokenDto
import com.example.shoppinglist.features.auth.data.dto.UserDto

interface AuthApi {

    suspend fun login(request: LoginRequestDto): UserDto

    suspend fun registration(request: LoginRequestDto): UserDto

    suspend fun refreshToken(refreshToken: String): TokenDto

    suspend fun check(): CheckTokenDto

    suspend fun recoverPassword(email: String)
}