package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.core.theme.ShoppingListTheme


@Stable
@Composable
fun SlButtons.SLTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    containerColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    disabledContainerColor: Color = Color.Transparent,
    disabledContentColor: Color = Color.Transparent,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    shape: RoundedCornerShape = RoundedCornerShape(50.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        shape = shape
    ) {
        Text(text = text, style = textStyle)
    }
}

@Preview
@Composable
fun PreviewSLTextButton() {
    ShoppingListTheme(dynamicColor = false) {
        SlButtons.SLTextButton(
            onClick = {},
            text = "Отменить",
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

