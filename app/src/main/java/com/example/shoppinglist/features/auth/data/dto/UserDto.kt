package com.example.shoppinglist.features.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("accessToker") val accessToker: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("userId") val userId: Long
)