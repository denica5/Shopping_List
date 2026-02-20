package com.example.shoppinglist.features.productLists.presentation.composables

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlDialogs
import com.example.shoppinglist.core.presentation.ui.components.SlDialogs.SlDeleteDialog
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsAction

object CustomDialogs {

    @Composable
    fun ShowCreateDialog(action: ProductListsAction.ShowCreateDialog) {
        var query by remember { mutableStateOf("") }

        SlDialogs.SlTextInputDialog(
            isShown = true,
            title = stringResource(id = R.string.dialog_add_list_title),
            textFieldValue = query,
            onValueChange = {query = it},
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

        SlDialogs.SlTextInputDialog(
            isShown = true,
            title = stringResource(id = R.string.renaming_list),
            textFieldValue = query,
            onValueChange = { query = it},
            labelText = stringResource(id = R.string.dialog_list_name_label),
            placeholder = stringResource(id = R.string.dialog_list_name_placeholder),
            confirmButtonText = stringResource(id = R.string.rename),
            dismissButtonText = stringResource(id = R.string.cancel),
            onConfirmClick = {   val updateList = action.productList.copy(
                name = query,
                icon = action.productList.icon
            )

                action.onPosBtnClick(updateList) },
            onDismissClick = { action.onCancelBtnClick() },
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
        SlDeleteDialog(
            isShown = true,
            icon = ImageVector.vectorResource(R.drawable.attention),
            title = stringResource(
                R.string.delete_list_name,
                action.productList.name
            ),
            onConfirmClick = { action.onPosBtnClick(action.productList.id) },
            onDismissClick = { action.onCancelBtnClick() },
            confirmButtonText = stringResource(R.string.delete),
            dismissButtonText = stringResource(R.string.cancel)
        )
    }
}