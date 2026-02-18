package com.example.shoppinglist.features.productLists.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlDialogs
import com.example.shoppinglist.core.theme.AppDimens.HeightBase
import com.example.shoppinglist.core.theme.AppDimens.PaddingBase
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsAction

object CustomDialogs {

    @Composable
    fun ShowCreateDialog(action: ProductListsAction.ShowCreateDialog) {
        var query by remember { mutableStateOf("") }

        SlDialogs.SlTextInputDialog(
            isShown = true,
            title = stringResource(id = R.string.dialog_add_list_title),
            textFieldValue = query,
            onValueChange = { newvalue -> query = newvalue },
            labelText = stringResource(id = R.string.dialog_list_name_label),
            placeholder = stringResource(id = R.string.dialog_list_name_placeholder),
            icon = ImageVector.vectorResource(R.drawable.add),
            confirmButtonText = stringResource(id = R.string.dialog_button_create),
            dismissButtonText = stringResource(id = R.string.dialog_button_cancel),
            onConfirmClick = { action.onPosBtnClick(query, null) },
            onDismissClick = { action.onCancelBtnClick() },
        )
    }

    @Composable
    fun ShowEditDialog(action: ProductListsAction.ShowEditDialog) {
        var query by remember { mutableStateOf(action.productList.name) }
        val focusManager = LocalFocusManager.current

        AlertDialog(
            onDismissRequest = {
                action.onCancelBtnClick()
            },
            title = { Text(stringResource(R.string.renaming_list)) },
            text = {
                BasicTextField(
                    value = query,
                    onValueChange = { newValue ->
                        query = newValue
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    cursorBrush = SolidColor(Blue),
                    singleLine = true,
                    modifier = Modifier
                        .height(HeightBase)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(
                            start = PaddingBase,
                            end = PaddingBase,
                            top = 16.dp,
                            bottom = 16.dp
                        ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {

                    val updateList = action.productList.copy(
                        name = query,
                        icon = action.productList.icon
                    )

                    action.onPosBtnClick(updateList)
                }) {
                    Text(
                        stringResource(R.string.rename),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    action.onCancelBtnClick()
                }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }

    @Composable
    fun ShowDeleteAllDialog(action: ProductListsAction.ShowDeleteAllDialog) {
        AlertDialog(
            onDismissRequest = {
                action.onCancelBtnClick()
            },
            title = {
                Text(
                    stringResource(R.string.delete_all_lists),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    action.onPosBtnClick()
                }) {
                    Text(
                        stringResource(R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    action.onCancelBtnClick()
                }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.attention),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
    }

    @Composable
    fun ShowDeleteDialog(action: ProductListsAction.ShowDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                action.onCancelBtnClick()
            },
            title = {
                Text(
                    stringResource(
                        R.string.delete_list_name,
                        action.productList.name
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    action.onPosBtnClick(action.productList.id)
                }) {
                    Text(
                        stringResource(R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    action.onCancelBtnClick()
                }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.attention),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )
    }

}