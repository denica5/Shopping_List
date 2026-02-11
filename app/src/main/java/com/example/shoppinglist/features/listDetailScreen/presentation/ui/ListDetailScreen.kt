package com.example.shoppinglist.features.listDetailScreen.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlDialogs
import com.example.shoppinglist.core.presentation.ui.components.SlElevatedButton
import com.example.shoppinglist.core.presentation.ui.components.SwipeCardController
import com.example.shoppinglist.core.theme.ShoppingListTheme
import com.example.shoppinglist.features.listDetailScreen.presentation.components.AddEditProductSheet
import com.example.shoppinglist.features.listDetailScreen.presentation.components.EmptyProductsContent
import com.example.shoppinglist.features.listDetailScreen.presentation.components.MenuContent
import com.example.shoppinglist.features.listDetailScreen.presentation.components.ProductItem
import com.example.shoppinglist.features.listDetailScreen.presentation.components.ProductsAppBar
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailAction
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailEvent
import com.example.shoppinglist.features.listDetailScreen.presentation.model.SortMode
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    listName: String,
    onBackClick: () -> Unit,
    viewModel: ListDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val action = viewModel.action.collectAsStateWithLifecycle().value
    val swipeController = remember { SwipeCardController() }
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        viewModel.obtainEvent(ListDetailEvent.MoveProduct(from.index, to.index))
    }

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true,
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState,
    )

    val sheetColor by animateColorAsState(
        targetValue = if (bottomSheetState.targetValue == SheetValue.Expanded) {
            MaterialTheme.colorScheme.surfaceContainerLow
        } else {
            Color.Transparent
        },
        animationSpec = tween(durationMillis = 300),
        label = "sheetContainerColor",
    )

    LaunchedEffect(action) {
        when (action) {
            is ListDetailAction.NavigateBack -> {
                onBackClick()
                viewModel.clearAction()
            }
            null -> {}
        }
    }

    LaunchedEffect(state.isMenuSheetVisible) {
        if (state.isMenuSheetVisible) {
            bottomSheetState.expand()
        } else {
            bottomSheetState.partialExpand()
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { bottomSheetState.currentValue }
            .collect { sheetValue ->
                if (sheetValue == SheetValue.PartiallyExpanded) {
                    viewModel.obtainEvent(ListDetailEvent.DismissMenuSheet)
                }
            }
    }

    val navBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    ShoppingListTheme {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ProductsAppBar(
                    title = listName,
                    onBackClick = { viewModel.obtainEvent(ListDetailEvent.BackClick) },
                    onMenuClick = { viewModel.obtainEvent(ListDetailEvent.MenuClick) },
                )
            },
            sheetContent = {
                // без этой проверки просвечивал сквозь блок системных кнопок при пустом списке
                if (state.products.isNotEmpty()) {
                    MenuContent(
                        currentSortMode = state.sortMode,
                        isSortSubmenuVisible = state.isSortSubmenuVisible,
                        onSortClick = { viewModel.obtainEvent(ListDetailEvent.ToggleSortSubmenu) },
                        onSortModeSelected = {
                            viewModel.obtainEvent(ListDetailEvent.SetSortMode(it))
                        },
                        onDeleteAllClick = { viewModel.obtainEvent(ListDetailEvent.DeleteAllClick) },
                        onClearPurchasedClick = {
                            viewModel.obtainEvent(ListDetailEvent.ClearPurchasedClick)
                        },
                    )
                }
            },
            sheetDragHandle = {
                if (state.products.isNotEmpty()) {
                    BottomSheetDefaults.DragHandle()
                }
            },
            sheetPeekHeight = if (state.products.isNotEmpty()) 28.dp + navBarBottom else navBarBottom,
            sheetContainerColor = sheetColor,
            sheetShadowElevation = 0.dp,
            sheetTonalElevation = 0.dp,
            containerColor = MaterialTheme.colorScheme.background,
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                if (state.products.isEmpty()) {
                    EmptyProductsContent()
                } else {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        itemsIndexed(
                            items = state.products,
                            key = { _, product -> product.id },
                        ) { _, product ->
                            ReorderableItem(
                                reorderableLazyListState,
                                key = product.id,
                            ) { isDragging ->
                                ProductItem(
                                    product = product,
                                    showDragHandle = state.sortMode == SortMode.CUSTOM,
                                    isDragging = isDragging,
                                    controller = swipeController,
                                    dragModifier = if (state.sortMode == SortMode.CUSTOM) {
                                        Modifier.draggableHandle()
                                    } else {
                                        Modifier
                                    },
                                    onTogglePurchased = {
                                        viewModel.obtainEvent(
                                            ListDetailEvent.TogglePurchased(product)
                                        )
                                    },
                                    onEditClick = {
                                        viewModel.obtainEvent(
                                            ListDetailEvent.EditProductClick(product)
                                        )
                                    },
                                    onDeleteClick = {
                                        viewModel.obtainEvent(
                                            ListDetailEvent.DeleteProduct(product)
                                        )
                                    },
                                )
                            }
                        }
                    }
                }

                if (!state.isAddEditSheetVisible) {
                    SlButtons.SlElevatedButton(
                        onClick = { viewModel.obtainEvent(ListDetailEvent.AddProductClick) },
                        imageVector = ImageVector.vectorResource(id = R.drawable.plus),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                    )
                }
            }
        }

        if (state.isAddEditSheetVisible) {
            AddEditProductSheet(
                state = state,
                onNameChanged = { viewModel.obtainEvent(ListDetailEvent.InputNameChanged(it)) },
                onQuantityChanged = {
                    viewModel.obtainEvent(ListDetailEvent.InputQuantityChanged(it))
                },
                onUnitChanged = { viewModel.obtainEvent(ListDetailEvent.InputUnitChanged(it)) },
                onIncrement = { viewModel.obtainEvent(ListDetailEvent.IncrementQuantity) },
                onDecrement = { viewModel.obtainEvent(ListDetailEvent.DecrementQuantity) },
                onSave = { viewModel.obtainEvent(ListDetailEvent.SaveProductClick) },
                onDismiss = { viewModel.obtainEvent(ListDetailEvent.DismissAddEditSheet) },
            )
        }

        SlDialogs.SlDeleteDialog(
            isShown = state.isDeleteAllDialogVisible,
            icon = ImageVector.vectorResource(R.drawable.attention),
            title = "Удалить все товары?",
            onConfirmClick = { viewModel.obtainEvent(ListDetailEvent.ConfirmDeleteAll) },
            onDismissClick = { viewModel.obtainEvent(ListDetailEvent.DismissDeleteAllDialog) },
            confirmButtonText = "Удалить",
            dismissButtonText = "Отмена",
        )

        SlDialogs.SlDeleteDialog(
            isShown = state.isClearPurchasedDialogVisible,
            icon = ImageVector.vectorResource(R.drawable.attention),
            title = "Удалить\nвсе купленные\nтовары?",
            onConfirmClick = { viewModel.obtainEvent(ListDetailEvent.ConfirmClearPurchased) },
            onDismissClick = {
                viewModel.obtainEvent(ListDetailEvent.DismissClearPurchasedDialog)
            },
            confirmButtonText = "Удалить",
            dismissButtonText = "Отмена",
        )
    }
}
