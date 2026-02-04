package com.example.shoppinglist.core.ui.buttonLibrary

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.theme.ShoppingListTheme


@Stable
@Composable
fun ShoppingListButtons.Icons(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes src: Int
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
        modifier = modifier.size(48.dp)
    ) {
        Icon(painterResource(src), contentDescription = "Search")
    }
}

@Preview
@Composable
fun IconsPreview() {
    ShoppingListTheme(dynamicColor = false) {
        ShoppingListButtons.Icons(
            onClick = {},
            src = R.drawable.ic_launcher_foreground
        )
    }
}