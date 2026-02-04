package com.example.shoppinglist.core.ui.buttonLibrary

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.theme.ShoppingListTheme


@Stable
@Composable
fun SLButtons.SLIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    painter: Painter,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    containerColor: Color = Color.Transparent,
    disabledContainerColor: Color = Color.Transparent,
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = contentColor,
            containerColor = containerColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor,
        ),
        modifier = modifier.size(48.dp),
        enabled = enabled,
    ) {
        Icon(painter, contentDescription = "")

    }
}

@Preview
@Composable
fun SLIconPreview() {
    ShoppingListTheme(dynamicColor = false) {
        SLButtons.SLIcon(
            onClick = {},
            painter = painterResource(R.drawable.close),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.outlineVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            enabled = true
        )
    }
}