package com.example.shoppinglist.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val accessToker: String, val refreshToken: String, val userId: Long)