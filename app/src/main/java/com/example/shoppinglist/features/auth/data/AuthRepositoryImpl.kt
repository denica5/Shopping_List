package com.example.shoppinglist.features.auth.data

import com.example.shoppinglist.features.auth.domain.AuthRepository
import com.example.shoppinglist.core.utils.NetworkError
import com.example.shoppinglist.core.utils.Result
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
            val user = auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(user.user?.uid ?: "")
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.Error(NetworkError.CONFLICT)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // неверный пароль
            Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
        } catch (e: FirebaseAuthWeakPasswordException) {
            // пользователь заблокирован
            Result.Error(NetworkError.UNAUTHORIZED)
        } catch (e: Exception) {
            // остальные ошибки (сеть, таймаут, неизвестные)
            Result.Error(NetworkError.UNKNOWN)
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }
}