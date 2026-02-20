package com.example.shoppinglist.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object ProductLists : Route, NavKey

    @Serializable
    data class ListDetailScreen(val listId: Int, val listName: String) : Route, NavKey

    @Serializable
    data object LoginScreen : Route, NavKey

    @Serializable
    data object RegisterScreen: Route, NavKey

    @Serializable
    data object PasswordRecoveryScreen: Route, NavKey
}
