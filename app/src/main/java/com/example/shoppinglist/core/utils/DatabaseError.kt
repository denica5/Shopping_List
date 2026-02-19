package com.example.shoppinglist.core.utils

enum class DatabaseError : Error {
  INSERT_FAILED,
  UPDATE_FAILED,
  DELETE_FAILED,
  READ_FAILED,
  UNKNOWN
}
