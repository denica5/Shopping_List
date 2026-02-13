package com.example.shoppinglist.features.login.data

import com.example.shoppinglist.features.login.data.dto.CheckTokenDto
import com.example.shoppinglist.features.login.data.dto.LoginRequestDto
import com.example.shoppinglist.features.login.data.dto.TokenDto
import com.example.shoppinglist.features.login.data.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthApiImpl(
    private val client: HttpClient
) : AuthApi {

    override suspend fun login(
        request: LoginRequestDto
    ): UserDto {
        return client.post("auth/login") {
            setBody(request)
        }.body()
    }

    override suspend fun registration(
        request: LoginRequestDto
    ): UserDto {
        return client.post("auth/register") {
            setBody(request)
        }.body()
    }

    override suspend fun refreshToken(
        refreshToken: String
    ): TokenDto {
        return client.post("auth/refresh") {
            setBody(mapOf("refreshToken" to refreshToken))
        }.body()
    }

    override suspend fun check(): CheckTokenDto {
        return client.get("auth/check") {

        }.body()
    }

    override suspend fun recoverPassword(email: String) {
        val response = client.post("auth/recovery") {
            header("email", email)
        }

        when (response.status.value) {
            200 -> return
            400 -> throw IllegalArgumentException("Некорректный email")
            else -> throw Exception("Ошибка сервера: ${response.status}")
        }
    }
}