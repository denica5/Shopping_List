package com.example.shoppinglist.features.auth.data

import com.example.shoppinglist.features.auth.domain.AuthRepository
import com.example.shoppinglist.core.utils.NetworkError
import com.example.shoppinglist.core.utils.Result
import com.example.shoppinglist.features.auth.data.mappers.mapAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<String, NetworkError> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.Error(mapAuthException(e))
        }
    }

    override fun logout() {
        try {
            auth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapAuthException(e))
        }
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun resetPassword(email: String): Result<Unit, NetworkError> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(mapAuthException(e))
        }
    }

    override suspend fun register(email: String, password: String): Result<String, NetworkError> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.Error(mapAuthException(e))
        }

    }

}