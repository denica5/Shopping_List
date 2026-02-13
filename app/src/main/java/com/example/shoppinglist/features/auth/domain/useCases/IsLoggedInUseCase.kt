package com.example.shoppinglist.features.auth.domain.useCases

import com.example.shoppinglist.features.auth.domain.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return repository.isLoggedIn()
    }
}