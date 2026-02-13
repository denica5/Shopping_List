package com.example.shoppinglist.features.listDetailScreen.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.theme.AppColors
import com.example.shoppinglist.features.listDetailScreen.presentation.model.SortMode

@Composable
fun MenuSheetContent(
    currentSortMode: SortMode,
    isSortSubmenuVisible: Boolean,
    onSortClick: () -> Unit,
    onSortModeSelected: (SortMode) -> Unit,
    onDeleteAllClick: () -> Unit,
    onClearPurchasedClick: () -> Unit,
) {
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp + navBarPadding),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSortClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.sort),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.product_sort),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = when (currentSortMode) {
                        SortMode.ALPHABETICAL -> stringResource(R.string.product_sort_alphabetical)
                        SortMode.CUSTOM -> stringResource(R.string.product_sort_custom)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.Green,
                )
            }

            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )

            if (isSortSubmenuVisible) {
                SortSubmenu(
                    currentSortMode = currentSortMode,
                    onSortModeSelected = onSortModeSelected,
                )
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 24.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onDeleteAllClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.trash),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.product_delete_all),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClearPurchasedClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.broom),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.product_clear_purchased),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun SortSubmenu(
    currentSortMode: SortMode,
    onSortModeSelected: (SortMode) -> Unit,
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = {},
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .clickable { onSortModeSelected(SortMode.ALPHABETICAL) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.sort_by_alphabet),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.product_sort_alphabetical),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            RadioButton(
                selected = currentSortMode == SortMode.ALPHABETICAL,
                onClick = { onSortModeSelected(SortMode.ALPHABETICAL) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }

        Row(
            modifier = Modifier
                .clickable { onSortModeSelected(SortMode.CUSTOM) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.sort_custom),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.product_sort_custom),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            RadioButton(
                selected = currentSortMode == SortMode.CUSTOM,
                onClick = { onSortModeSelected(SortMode.CUSTOM) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}