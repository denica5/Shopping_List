package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglist.core.theme.ShoppingListTheme


@Stable
@Composable
fun SlButtons.SlTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
fun PreviewSlTextButton() {
    ShoppingListTheme(dynamicColor = false) {
        SlButtons.SlTextButton(
            text = "Отменить",
            onClick = {},
        )
    }
}