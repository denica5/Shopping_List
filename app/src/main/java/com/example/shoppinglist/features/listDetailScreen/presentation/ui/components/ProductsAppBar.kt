package com.example.shoppinglist.features.listDetailScreen.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.shoppinglist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsAppBar(
    title: String,
    showBackButton: Boolean = true,
    showMenuButton: Boolean = true,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = if (showBackButton) {
            {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            if (showMenuButton) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        painter = painterResource(R.drawable.dots),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}