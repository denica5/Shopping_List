package com.example.shoppinglist.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglist.core.theme.ShoppingListTheme

object SlTextFields {
    @Composable
    fun Focused(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        labelText: String = "",
        showLabel: Boolean = true,
        placeholder: String? = null,
        singleLine: Boolean = true,
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        labelColor: Color = MaterialTheme.colorScheme.primary,
        labelBackgroundColor: Color = Color.Unspecified,
        borderColor: Color = MaterialTheme.colorScheme.primary,
    ) {
        SlTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            labelText = labelText,
            showLabel = showLabel,
            placeholder = placeholder,
            singleLine = singleLine,
            textColor = textColor,
            textStyle = textStyle,
            labelColor = labelColor,
            labelBackgroundColor = labelBackgroundColor,
            borderColor = borderColor,
        )
    }

    @Composable
    fun Unfocused(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        labelText: String = "",
        showLabel: Boolean = true,
        placeholder: String? = null,
        singleLine: Boolean = true,
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        labelBackgroundColor: Color = Color.Unspecified,
        borderColor: Color = MaterialTheme.colorScheme.outline,
    ) {
        SlTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            labelText = labelText,
            showLabel = showLabel,
            placeholder = placeholder,
            singleLine = singleLine,
            textColor = textColor,
            textStyle = textStyle,
            labelColor = labelColor,
            labelBackgroundColor = labelBackgroundColor,
            borderColor = borderColor,
        )
    }
}

@Composable
private fun SlTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    showLabel: Boolean = true,
    placeholder: String? = null,
    singleLine: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    containerColor: Color = Color.Transparent,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    labelBackgroundColor: Color = Color.Unspecified,
    borderColor: Color = MaterialTheme.colorScheme.outline,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = true,
            textStyle = textStyle,
            label = if (showLabel && labelText.isNotEmpty()) {
                {
                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.bodySmall,
                        color = labelColor,
                        modifier = if (labelBackgroundColor != Color.Unspecified) {
                            Modifier.background(labelBackgroundColor)
                        } else {
                            Modifier
                        },
                    )
                }
            } else {
                null
            },
            placeholder = placeholder?.let { text ->
                {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.6f),
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedLabelColor = labelColor,
                unfocusedLabelColor = labelColor,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SlTextFieldPreview1() {
    ShoppingListTheme {
        SlTextFields.Unfocused(
            value = "Продукты",
            onValueChange = {},
            labelText = "Название списка",
            placeholder = "Введите название"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SlTextFieldPreview2() {
    ShoppingListTheme {
        SlTextFields.Focused(
            value = "Продукты",
            onValueChange = {},
            labelText = "Название списка",
            placeholder = "Введите название"
        )
    }
}


