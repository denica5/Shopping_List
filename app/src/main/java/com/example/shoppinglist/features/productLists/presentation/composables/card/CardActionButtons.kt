package com.example.shoppinglist.features.productLists.presentation.composables.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlIcon
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.presentation.ProductListsViewModel
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsEvent

@Composable
fun CardActionButtons(viewModel: ProductListsViewModel, list: ProductList) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SlButtons.SlIcon(
            onClick = {
                viewModel.obtainEvent(
                    ProductListsEvent.BtnEditInClick(
                        list
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            painter = painterResource(id = R.drawable.pencil),
        )
        Spacer(Modifier.width(8.dp))
        SlButtons.SlIcon(
            onClick = {
                viewModel.obtainEvent(
                    ProductListsEvent.BtnCopyInClick(
                        list
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            painter = painterResource(id = R.drawable.copy),
        )
        Spacer(Modifier.width(8.dp))
        SlButtons.SlIcon(
            onClick = {
                viewModel.obtainEvent(
                    ProductListsEvent.BtnDeleteInClick(
                        list
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            painter = painterResource(id = R.drawable.trash),
        )
    }
}