package com.example.shoppinglist.features.auth.domain.model

data class User(val accessToker: String, val refreshToken: String, val userId: Long)
