package com.example.shoppinglist.core.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.shoppinglist.features.listDetailScreen.presentation.ListDetailScreen
import com.example.shoppinglist.features.productLists.presentation.ProductListsScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.MyListsScreen::class, Route.MyListsScreen.serializer())
                    subclass(Route.ListDetailScreen::class, Route.ListDetailScreen.serializer())
                }
            }
        },
        Route.MyListsScreen
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
                is Route.MyListsScreen -> {
                    NavEntry(key) {
                        ProductListsScreen(
                            onItemClick =
                                {
                                    backStack.add(Route.ListDetailScreen(it))
                                }
                        )
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
                else -> {
                    error("Unknown NavKey: $key")
                }
            }
        }
    )
}