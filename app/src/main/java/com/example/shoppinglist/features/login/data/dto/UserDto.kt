package com.example.shoppinglist.features.login.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val accessToker: String, val refreshToken: String, val userId: Long)