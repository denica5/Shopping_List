package com.example.shoppinglist.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.shoppinglist.core.presentation.adaptive.TabletListsAndDetailsLayout
import com.example.shoppinglist.features.listDetailScreen.presentation.ui.ListDetailScreen
import com.example.shoppinglist.features.auth.presentation.ui.LoginScreen
import com.example.shoppinglist.features.auth.presentation.ui.PasswordRecoveryScreen
import com.example.shoppinglist.features.auth.presentation.ui.RegisterScreen
import com.example.shoppinglist.features.productLists.presentation.ProductListsScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.ProductLists::class, Route.ProductLists.serializer())
                    subclass(Route.ListDetailScreen::class, Route.ListDetailScreen.serializer())
                    subclass(Route.LoginScreen::class, Route.LoginScreen.serializer())
                    subclass(Route.RegisterScreen::class, Route.RegisterScreen.serializer())
                    subclass(Route.PasswordRecoveryScreen::class, Route.PasswordRecoveryScreen.serializer())
                }
            }
        },
        Route.LoginScreen
    )

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator { false }
        ),
        entryProvider = { key ->
            when (key) {
                is Route.ProductLists -> {
                    NavEntry(key) {
                        if (isTabletMode()) {
                            TabletListsAndDetailsLayout()
                        } else {
                            ProductListsScreen(
                                onItemClick =
                                    {
                                        backStack.add(Route.ListDetailScreen(it))
                                    }
                            )
                        }
                    }
                }

                is Route.ListDetailScreen -> {
                    NavEntry(key) {
                        ListDetailScreen(
                            listName = key.todo,
                            onBackClick = { backStack.removeLastOrNull() },
                        )
                    }
                }

                is Route.LoginScreen -> {
                    NavEntry(key) {
                        LoginScreen(
                            onSignInClick = {
                                backStack.add(Route.ProductLists)
                            },
                            onCreateNewAccountClick = {
                                backStack.add(Route.RegisterScreen)
                            },
                            onForgotPasswordClick = {
                                backStack.add(Route.PasswordRecoveryScreen)
                            }
                        )
                    }
                }

                is Route.RegisterScreen -> {
                    NavEntry(key) {
                        RegisterScreen(
                            onRegisterClick = { backStack.remove(key) },
                            onBackArrowClick = { backStack.remove(key) })
                    }
                }

                is Route.PasswordRecoveryScreen -> {
                    NavEntry(key) {
                        PasswordRecoveryScreen(
                            onPasswordRecoveryClick = { backStack.remove(key) },
                            onBackArrowClick = { backStack.remove(key) })
                    }
                }

                else -> {
                    error("Unknown NavKey: $key")
                }
            }
        }
    )
}

@Composable
private fun isTabletMode(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp >= 840
}