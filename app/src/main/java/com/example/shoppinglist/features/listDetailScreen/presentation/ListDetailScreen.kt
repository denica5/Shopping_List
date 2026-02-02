package com.example.shoppinglist.features.listDetailScreen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.shoppinglist.core.navigation.Route

@Composable
fun ListDetailScreen(todo: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) { Text(todo) }
}