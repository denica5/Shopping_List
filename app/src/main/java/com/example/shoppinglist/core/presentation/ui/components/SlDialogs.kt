package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.shoppinglist.R
import com.example.shoppinglist.core.theme.AppDimens
import com.example.shoppinglist.core.theme.ShoppingListTheme

object SlDialogs {
    @Stable
    @Composable
    fun SlTextInputDialog(
        modifier: Modifier = Modifier,
        isShown: Boolean,
        title: String,
        textFieldValue: String,
        onValueChange: (String) -> Unit,
        labelText: String,
        placeholder: String? = null,
        icon: ImageVector? = null,
        onConfirmClick: () -> Unit,
        onDismissClick: () -> Unit,
        confirmButtonText: String,
        dismissButtonText: String,
    ) {
        if (!isShown) return

        Dialog(onDismissRequest = onDismissClick) {
            Surface(
                modifier = modifier
                    .width(AppDimens.DialogWidth),
                shape = RoundedCornerShape(AppDimens.RadiusDialogDefault),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = AppDimens.DialogPaddingHorizontal,
                            vertical = AppDimens.DialogPaddingVertical
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.height(AppDimens.DialogIconTopSpacing))
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(AppDimens.DialogContentTopSpacing))

                    SlTextFields.SlInputTextField(
                        value = textFieldValue,
                        onValueChange = onValueChange,
                        labelText = labelText,
                        placeholder = placeholder,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SlTextFieldDefaults.colors(
                            labelBackgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        ),
                    )

                    Spacer(modifier = Modifier.height(AppDimens.DialogContentTopSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SlButtons.SLTextButton(
                            text = dismissButtonText,
                            onClick = onDismissClick,
                            contentColor = MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.width(AppDimens.DialogTextButtonsSpacing))

                        SlButtons.SLTextButton(
                            text = confirmButtonText,
                            onClick = onConfirmClick,
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

    @Stable
    @Composable
    fun SlDeleteDialog(
        modifier: Modifier = Modifier,
        isShown: Boolean,
        icon: ImageVector,
        title: String,
        message: String? = null,
        onConfirmClick: () -> Unit,
        onDismissClick: () -> Unit,
        confirmButtonText: String,
        dismissButtonText: String,
    ) {
        if (!isShown) return

        Dialog(onDismissRequest = onDismissClick) {
            Surface(
                modifier = modifier
                    .width(AppDimens.DialogWidth),
                shape = RoundedCornerShape(AppDimens.RadiusDialogDefault),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = AppDimens.DialogPaddingHorizontal,
                            vertical = AppDimens.DialogPaddingVertical
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Spacer(modifier = Modifier.height(AppDimens.DialogIconTopSpacing))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )

                    if (message != null) {
                        Spacer(modifier = Modifier.height(AppDimens.DialogMessageTopSpacing))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(AppDimens.DialogContentTopSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SlButtons.SlPillButton(
                            text = dismissButtonText,
                            onClick = onDismissClick,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        )

                        Spacer(modifier = Modifier.width(AppDimens.DialogPillButtonsSpacing))

                        SlButtons.SlPillButton(
                            text = confirmButtonText,
                            onClick = onConfirmClick,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSLTextInputDialog() {
    ShoppingListTheme(darkTheme = false, dynamicColor = false) {
        val listName = stringResource(id = R.string.dialog_preview_list_name)
        val (value, onValueChange) = remember { mutableStateOf(listName) }

        SlDialogs.SlTextInputDialog(
            isShown = true,
            title = stringResource(id = R.string.dialog_add_list_title),
            textFieldValue = value,
            onValueChange = onValueChange,
            labelText = stringResource(id = R.string.dialog_list_name_label),
            placeholder = stringResource(id = R.string.dialog_list_name_placeholder),
            icon = ImageVector.vectorResource(R.drawable.add),
            confirmButtonText = stringResource(id = R.string.dialog_button_create),
            dismissButtonText = stringResource(id = R.string.dialog_button_cancel),
            onConfirmClick = {},
            onDismissClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSLDeleteDialog() {
    ShoppingListTheme(darkTheme = false, dynamicColor = false) {
        val listName = stringResource(id = R.string.dialog_preview_list_name)
        SlDialogs.SlDeleteDialog(
            isShown = true,
            icon = ImageVector.vectorResource(R.drawable.attention),
            title = stringResource(id = R.string.dialog_delete_list_title, listName),
            message = null,
            confirmButtonText = stringResource(id = R.string.dialog_button_delete),
            dismissButtonText = stringResource(id = R.string.dialog_button_cancel),
            onConfirmClick = {},
            onDismissClick = {},
        )
    }
}


