package com.example.shoppinglist.features.auth.domain.useCases

import com.example.shoppinglist.core.utils.NetworkError
import com.example.shoppinglist.core.utils.Result
import com.example.shoppinglist.features.auth.domain.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String, NetworkError> {
        return repository.register(email, password)
    }
}