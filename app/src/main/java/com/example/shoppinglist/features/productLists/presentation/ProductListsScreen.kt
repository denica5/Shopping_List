package com.example.shoppinglist.features.productLists.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlElevatedButton
import com.example.shoppinglist.core.theme.AppDimens.PaddingBase
import com.example.shoppinglist.core.theme.ShoppingListTheme
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.presentation.composables.CustomDialogs
import com.example.shoppinglist.features.productLists.presentation.composables.DefaultAppBar
import com.example.shoppinglist.features.productLists.presentation.composables.EmptyContent
import com.example.shoppinglist.features.productLists.presentation.composables.card.ListCard
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsAction
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListsScreen(
    onItemClick: (String) -> Unit,
    viewModel: ProductListsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val action = viewModel.action.collectAsStateWithLifecycle().value
    var showDialog by remember { mutableStateOf(false) }

    when (action) {
        is ProductListsAction.ShowCreateDialog -> {
            CustomDialogs.ShowCreateDialog(action)
        }

        is ProductListsAction.ShowDeleteAllDialog -> {
            CustomDialogs.ShowDeleteAllDialog(action)
        }

        is ProductListsAction.ShowEditDialog -> {
            CustomDialogs.ShowEditDialog(action)
        }

        is ProductListsAction.ShowDeleteDialog -> {
            CustomDialogs.ShowDeleteDialog(action)
        }
        else -> {
            showDialog = false
        }
    }

    ShoppingListTheme {
        Scaffold(

            topBar = {
                DefaultAppBar(viewModel)

            },
            floatingActionButton = {
                SlButtons.SlElevatedButton(
                    onClick = {
                        viewModel.obtainEvent(ProductListsEvent.BtnCreateInClick)
                    },
                    iconPainter = painterResource(id = R.drawable.plus),
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(PaddingBase)
            ) {
                if (state.productLists.isEmpty())
                    EmptyContent()
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.productLists) { list: ProductList ->
                        ListCard(list, viewModel)
                    }
                }
            }
        }
    }
}
