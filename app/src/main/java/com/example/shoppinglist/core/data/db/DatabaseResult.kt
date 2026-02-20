package com.example.shoppinglist.core.data.db

import com.example.shoppinglist.core.utils.DatabaseError
import java.io.IOException
import com.example.shoppinglist.core.utils.Result as DomainResult

sealed class DatabaseResult<out T> {

  data class Success<out T>(val data: T) : DatabaseResult<T>()

  data class Error(val exception: IOException) : DatabaseResult<Nothing>()

  val isSuccess: Boolean get() = this is Success

  val isError: Boolean get() = this is Error

  fun getOrNull(): T? = when (this) {
    is Success -> data
    is Error -> null
  }

  fun exceptionOrNull(): IOException? = when (this) {
    is Success -> null
    is Error -> exception
  }
}

inline fun <T> safeDbCall(block: () -> T): DatabaseResult<T> {
  return try {
    DatabaseResult.Success(block())
  } catch (e: IOException) {
    DatabaseResult.Error(e)
  }
}

fun <T> DatabaseResult<T>.toDomainResult(
  errorType: DatabaseError = DatabaseError.UNKNOWN
): DomainResult<T, DatabaseError> {
  return when (this) {
    is DatabaseResult.Success -> DomainResult.Success(data)
    is DatabaseResult.Error -> DomainResult.Error(errorType)
  }
}
