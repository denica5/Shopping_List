package com.example.shoppinglist.features.listDetailScreen.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlDialogs
import com.example.shoppinglist.core.presentation.ui.components.SlElevatedButton
import com.example.shoppinglist.core.presentation.ui.components.SwipeCardController
import com.example.shoppinglist.core.theme.AppDimens
import com.example.shoppinglist.core.theme.ShoppingListTheme
import com.example.shoppinglist.features.listDetailScreen.presentation.components.AddEditSheetContent
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailAction
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailEvent
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailSheet
import com.example.shoppinglist.features.listDetailScreen.presentation.model.SortMode
import com.example.shoppinglist.features.listDetailScreen.presentation.ui.components.EmptyProductsContent
import com.example.shoppinglist.features.listDetailScreen.presentation.ui.components.MenuSheetContent
import com.example.shoppinglist.features.listDetailScreen.presentation.ui.components.ProductItem
import com.example.shoppinglist.features.listDetailScreen.presentation.ui.components.ProductsAppBar
import com.example.shoppinglist.features.listDetailScreen.presentation.viewmodel.ListDetailViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    listName: String,
    onBackClick: () -> Unit,
    showBackButton: Boolean = true,
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
    )

    val prevProductCount = remember { mutableStateOf(state.products.size) }

    LaunchedEffect(state.products.size) {
        if (state.activeSheet == ListDetailSheet.AddEdit && state.products.size != prevProductCount.value) {
            prevProductCount.value = state.products.size
            bottomSheetState.partialExpand()
            viewModel.dismissAddEditSheetAfterSave()
        }
    }

    LaunchedEffect(action) {
        when (action) {
            is ListDetailAction.NavigateBack -> {
                onBackClick()
                viewModel.clearAction()
            }
            null -> {}
        }
    }

    LaunchedEffect(state.activeSheet) {
        if (state.activeSheet != null) {
            bottomSheetState.expand()
        } else {
            bottomSheetState.partialExpand()
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { bottomSheetState.currentValue }
            .collect { sheetValue ->
                if (sheetValue == SheetValue.PartiallyExpanded) {
                    viewModel.obtainEvent(ListDetailEvent.DismissSheet)
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
                    showBackButton = showBackButton,
                    showMenuButton = state.products.isNotEmpty(),
                    onBackClick = { viewModel.obtainEvent(ListDetailEvent.BackClick) },
                    onMenuClick = { viewModel.obtainEvent(ListDetailEvent.MenuClick) },
                )
            },
            sheetContent = {
                // без этой проверки просвечивал сквозь блок системных кнопок при пустом списке
                if (state.activeSheet == ListDetailSheet.AddEdit) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                end = AppDimens.ListDetailAddEditActionEndPadding,
                                bottom = AppDimens.ListDetailAddEditActionBottomPadding,
                            ),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        if (!state.inputName.isEmpty()) {
                            SlButtons.SlElevatedButton(
                                onClick = { viewModel.obtainEvent(ListDetailEvent.SaveProductClick) },
                                iconPainter = painterResource(id = R.drawable.check),
                            )
                        }
                    }

                    AddEditSheetContent(
                        state = state,
                        onNameChanged = { viewModel.obtainEvent(ListDetailEvent.InputNameChanged(it)) },
                        onQuantityChanged = {
                            viewModel.obtainEvent(ListDetailEvent.InputQuantityChanged(it))
                        },
                        onUnitChanged = { viewModel.obtainEvent(ListDetailEvent.InputUnitChanged(it)) },
                        onIncrement = { viewModel.obtainEvent(ListDetailEvent.IncrementQuantity) },
                        onDecrement = { viewModel.obtainEvent(ListDetailEvent.DecrementQuantity) },
                    )
                } else if (state.products.isNotEmpty()) {
                    MenuSheetContent(
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
                if (state.products.isNotEmpty() && state.activeSheet != ListDetailSheet.AddEdit) {
                    BottomSheetDefaults.DragHandle()
                }
            },
            sheetPeekHeight = if (state.products.isNotEmpty()) {
                AppDimens.ListDetailMenuSheetPeekHeight + navBarBottom
            } else {
                navBarBottom
            },
            sheetContainerColor = if (state.activeSheet == ListDetailSheet.AddEdit) {
                Color.Transparent
            } else {
                sheetColor
            },
            sheetShadowElevation = AppDimens.Zero,
            sheetTonalElevation = AppDimens.Zero,
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

                if (state.activeSheet != ListDetailSheet.AddEdit) {
                    SlButtons.SlElevatedButton(
                        onClick = { viewModel.obtainEvent(ListDetailEvent.AddProductClick) },
                        iconPainter = painterResource(id = R.drawable.plus),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(AppDimens.PaddingBase),
                    )
                }
            }
        }

        SlDialogs.SlDeleteDialog(
            isShown = state.isDeleteAllDialogVisible,
            icon = ImageVector.vectorResource(R.drawable.attention),
            title = stringResource(R.string.dialog_delete_all_products),
            onConfirmClick = { viewModel.obtainEvent(ListDetailEvent.ConfirmDeleteAll) },
            onDismissClick = { viewModel.obtainEvent(ListDetailEvent.DismissDeleteAllDialog) },
            confirmButtonText = stringResource(R.string.dialog_button_delete),
            dismissButtonText = stringResource(R.string.dialog_button_cancel),
        )

        SlDialogs.SlDeleteDialog(
            isShown = state.isClearPurchasedDialogVisible,
            icon = ImageVector.vectorResource(R.drawable.attention),
            title = stringResource(R.string.dialog_clear_purchased_products),
            onConfirmClick = { viewModel.obtainEvent(ListDetailEvent.ConfirmClearPurchased) },
            onDismissClick = {
                viewModel.obtainEvent(ListDetailEvent.DismissClearPurchasedDialog)
            },
            confirmButtonText = stringResource(R.string.dialog_button_delete),
            dismissButtonText = stringResource(R.string.dialog_button_cancel),
        )
    }
}