package com.example.shoppinglist.features.login.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(val accessToker: String, val refreshToken: String) {
}