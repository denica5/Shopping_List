package com.example.shoppinglist.core.presentation.adaptive

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.features.listDetailScreen.presentation.ListDetailScreen
import com.example.shoppinglist.features.listDetailScreen.presentation.ui.components.ProductsAppBar
import com.example.shoppinglist.features.productLists.presentation.ProductListsScreen

@Composable
fun TabletListsAndDetailsLayout() {
    var selectedListName by rememberSaveable { mutableStateOf<String?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ProductListsScreen(
                onItemClick = { selectedListName = it },
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.outlineVariant)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val detailTitle = selectedListName
            if (detailTitle == null) {
                EmptyDetailPlaceholder()
            } else {
                ListDetailScreen(
                    listName = detailTitle,
                    onBackClick = {},
                    showBackButton = false,
                )
            }
        }
    }
}

@Composable
private fun EmptyDetailPlaceholder() {
    val navBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(modifier = Modifier.fillMaxSize()) {
        ProductsAppBar(
            title = "",
            showBackButton = false,
            showMenuButton = false,
            onBackClick = {},
            onMenuClick = {},
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 44.dp)
                .padding(bottom = navBarBottom),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.illustration_product_list),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Выберите список продуктов",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // костыль, чтобы избежать скачка плейсхолдера при выборе списка продуктов, по другому никак не получалось
            Text(
                text = "\n",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}