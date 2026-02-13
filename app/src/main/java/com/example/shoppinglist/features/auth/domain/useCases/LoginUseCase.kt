package com.example.shoppinglist.features.auth.domain.useCases

import com.example.shoppinglist.features.auth.domain.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) {
        repository.login(email, password)
    }
}