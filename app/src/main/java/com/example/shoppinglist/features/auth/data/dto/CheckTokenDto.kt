package com.example.shoppinglist.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CheckTokenDto(val isValid: Boolean)
