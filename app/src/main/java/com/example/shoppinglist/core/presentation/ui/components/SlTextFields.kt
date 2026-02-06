package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppinglist.core.theme.ShoppingListTheme

object SlTextFields {

    @Composable
    fun SlInputTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        labelText: String = "",
        placeholder: String? = null,
        singleLine: Boolean = true,
        textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        colors: SlTextFieldColors = SlTextFieldDefaults.colors(),
    ) {
        val safeValue = value.take(64)
        SlTextField(
            value = safeValue,
            onValueChange = { newValue ->
                onValueChange(newValue.take(64))
            },
            modifier = modifier,
            labelText = labelText,
            placeholder = placeholder,
            singleLine = singleLine,
            textColor = colors.textColor,
            textStyle = textStyle,
            labelColor = colors.labelColor,
            labelBackgroundColor = colors.labelBackgroundColor,
            borderColor = colors.borderColor,
            keyboardOptions = KeyboardOptions.Default,
        )
    }

    @Composable
    fun SlInputNumberField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        labelText: String = "",
        placeholder: String? = null,
        singleLine: Boolean = true,
        textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        colors: SlTextFieldColors = SlTextFieldDefaults.colors(),
    ) {
        fun filter(input: String): String =
            input.filter { it.isDigit() || it == '.' }.take(10)

        val safeValue = filter(value)
        val numberKeyboard = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal
        )

        SlTextField(
            value = safeValue,
            onValueChange = { newValue ->
                onValueChange(filter(newValue))
            },
            modifier = modifier,
            labelText = labelText,
            placeholder = placeholder,
            singleLine = singleLine,
            textColor = colors.textColor,
            textStyle = textStyle,
            labelColor = colors.labelColor,
            labelBackgroundColor = colors.labelBackgroundColor,
            borderColor = colors.borderColor,
            keyboardOptions = numberKeyboard,
        )
    }

    @Composable
    private fun SlTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        labelText: String = "",
        placeholder: String? = null,
        singleLine: Boolean = true,
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        containerColor: Color = Color.Transparent,
        labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        labelBackgroundColor: Color = Color.Unspecified,
        borderColor: Color = MaterialTheme.colorScheme.outline,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    ) {
        Column(modifier = modifier) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                enabled = true,
                textStyle = textStyle,
                label = if (labelText.isNotEmpty()) {
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
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor.copy(alpha = 0.6f),
                        )
                    }
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions.Default,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = labelColor,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = borderColor,
                )
            )
        }
    }
}

data class SlTextFieldColors(
    val textColor: Color,
    val labelColor: Color,
    val labelBackgroundColor: Color,
    val borderColor: Color,
)

object SlTextFieldDefaults {
    @Composable
    fun colors(
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        labelBackgroundColor: Color = MaterialTheme.colorScheme.surface,
        borderColor: Color = MaterialTheme.colorScheme.outline,
    ): SlTextFieldColors =
        SlTextFieldColors(
            textColor = textColor,
            labelColor = labelColor,
            labelBackgroundColor = labelBackgroundColor,
            borderColor = borderColor,
        )
}

@Preview(showBackground = true)
@Composable
private fun SlTextFieldPreview() {
    ShoppingListTheme {
        Surface(color = Color(0xFFF4E6DA)) {
            SlTextFields.SlInputTextField(
                value = "Продукты",
                onValueChange = {},
                labelText = "Название списка",
                placeholder = "Введите название"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SlNumberFieldPreview() {
    ShoppingListTheme {
        Surface(color = Color(0xFFF4E6DA)) {
            SlTextFields.SlInputNumberField(
                value = "12.5",
                onValueChange = {},
                labelText = "Количество",
                placeholder = "0"
            )
        }
    }
}


