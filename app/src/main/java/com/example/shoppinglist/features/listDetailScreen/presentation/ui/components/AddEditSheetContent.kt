package com.example.shoppinglist.features.listDetailScreen.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlIcon
import com.example.shoppinglist.core.presentation.ui.components.SlTextFieldDefaults
import com.example.shoppinglist.core.presentation.ui.components.SlTextFields
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ListDetailState
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ProductUnit

@Composable
fun AddEditSheetContent(
    state: ListDetailState,
    onNameChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onUnitChanged: (ProductUnit) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        tonalElevation = 0.dp,
    ) {
        AddEditProductContent(
            state = state,
            onNameChanged = onNameChanged,
            onQuantityChanged = onQuantityChanged,
            onUnitChanged = onUnitChanged,
            onIncrement = onIncrement,
            onDecrement = onDecrement,
        )
    }
}

@Composable
private fun AddEditProductContent(
    state: ListDetailState,
    onNameChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onUnitChanged: (ProductUnit) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    var isUnitDropdownExpanded by remember { mutableStateOf(false) }
    val canDecrement = (state.inputQuantity.toDoubleOrNull() ?: 0.0) > 0.0
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val navBarBottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    // переписал боттом шиты на BottomSheetScaffold, начались траблы с клавиатурой, только такой костыль получилось придумать
    val imeBottomPadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding() / 2
    val bottomInsetPadding = maxOf(navBarBottomPadding, imeBottomPadding)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 24.dp + bottomInsetPadding),
    ) {
        val sheetLabelColors = SlTextFieldDefaults.colors(
            labelBackgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
        )

        // ввод названия продукта
        SlTextFields.SlInputTextField(
            value = state.inputName,
            onValueChange = onNameChanged,
            labelText = stringResource(R.string.product_textfield_lable),
            placeholder = stringResource(R.string.add_new_product_placeholder),
            modifier = Modifier.fillMaxWidth(),
            colors = sheetLabelColors,
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ввод количества продукта
            SlTextFields.SlInputNumberField(
                value = state.inputQuantity,
                onValueChange = onQuantityChanged,
                labelText = stringResource(R.string.product_quantity_label),
                placeholder = "0",
                modifier = Modifier.weight(1f),
                colors = sheetLabelColors,
            )

            Spacer(Modifier.width(8.dp))

            Box {
                // выбор единиц измерения продукта
                OutlinedTextField(
                    value = state.inputUnit?.label ?: "",
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = {
                        Text(
                            text = stringResource(R.string.product_unit_label),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow),
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.product_unit_placeholder),
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        cursorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .width(120.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isUnitDropdownExpanded = true
                            }
                        },
                )

                DropdownMenu(
                    expanded = isUnitDropdownExpanded,
                    onDismissRequest = {
                        isUnitDropdownExpanded = false
                        focusManager.clearFocus()
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    ProductUnit.entries.forEach { unit ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = unit.label,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                            onClick = {
                                onUnitChanged(unit)
                                isUnitDropdownExpanded = false
                                focusManager.clearFocus()
                            },
                        )
                    }
                }
            }

            Spacer(Modifier.width(8.dp))

            // кнопка минус
            SlButtons.SlIcon(
                onClick = onDecrement,
                painter = painterResource(R.drawable.minus),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                enabled = canDecrement,
            )

            Spacer(Modifier.width(4.dp))

            // кнопка плюс
            SlButtons.SlIcon(
                onClick = onIncrement,
                painter = painterResource(R.drawable.plus),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}