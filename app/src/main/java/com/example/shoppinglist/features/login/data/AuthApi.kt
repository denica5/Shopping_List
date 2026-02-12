package com.example.shoppinglist.features.login.data

import com.example.shoppinglist.features.login.data.dto.CheckTokenDto
import com.example.shoppinglist.features.login.data.dto.LoginRequestDto
import com.example.shoppinglist.features.login.data.dto.TokenDto
import com.example.shoppinglist.features.login.data.dto.UserDto

interface AuthApi {

    suspend fun login(request: LoginRequestDto): UserDto

    suspend fun registration(request: LoginRequestDto): UserDto

    suspend fun refreshToken(refreshToken: String): TokenDto

    suspend fun check(): CheckTokenDto

    suspend fun recoverPassword(email: String)
}