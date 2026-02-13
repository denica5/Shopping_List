package com.example.shoppinglist.features.login.domain.model

data class User(val accessToker: String, val refreshToken: String, val userId: Long)
