package com.example.shoppinglist.features.listDetailScreen.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlIcon
import com.example.shoppinglist.core.presentation.ui.components.SwipeCardController
import com.example.shoppinglist.core.presentation.ui.components.SwipeContainer
import com.example.shoppinglist.features.listDetailScreen.presentation.model.ProductUi

@Composable
fun ProductItem(
    product: ProductUi,
    showDragHandle: Boolean,
    isDragging: Boolean,
    controller: SwipeCardController,
    dragModifier: Modifier = Modifier,
    onTogglePurchased: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val elevation by animateDpAsState(
        targetValue = if (isDragging) 4.dp else 0.dp,
        label = "dragElevation",
    )

    val swipeActions: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SlButtons.SlIcon(
                onClick = onEditClick,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                painter = painterResource(id = R.drawable.pencil),
            )
            Spacer(Modifier.width(4.dp))
            SlButtons.SlIcon(
                onClick = onDeleteClick,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                painter = painterResource(id = R.drawable.trash),
            )
        }
    }

    Surface(shadowElevation = elevation) {
        SwipeContainer(
            id = product.id,
            controller = controller,
            maxOffset = 120.dp,
            mainBackground = { swipeActions() },
            extendedBackground = { swipeActions() },
            content = {
                ProductItemContent(
                    product = product,
                    showDragHandle = showDragHandle,
                    dragModifier = dragModifier,
                    onTogglePurchased = onTogglePurchased,
                )
            },
        )
    }
}

@Composable
private fun ProductItemContent(
    product: ProductUi,
    showDragHandle: Boolean,
    dragModifier: Modifier = Modifier,
    onTogglePurchased: () -> Unit,
) {
    val contentAlpha = if (product.isPurchased) 0.5f else 1f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularCheckbox(
                checked = product.isPurchased,
                onClick = onTogglePurchased,
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .alpha(contentAlpha),
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (product.quantity > 0.0) {
                    Text(
                        text = if (product.unit != null) {
                            "${formatDisplayQuantity(product.quantity)} ${product.unit.label}"
                        } else {
                            formatDisplayQuantity(product.quantity)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (showDragHandle) {
                Icon(
                    painter = painterResource(R.drawable.drag_handle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(24.dp)
                        .then(dragModifier),
                )
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 0.5.dp,
        )
    }
}

@Composable
private fun CircularCheckbox(
    checked: Boolean,
    onClick: () -> Unit,
) {
    if (checked) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(16.dp),
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = CircleShape,
                )
                .padding(2.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape,
                )
                .clickable(onClick = onClick),
        )
    }
}

private fun formatDisplayQuantity(value: Double): String {
    return if (value == value.toLong().toDouble()) {
        value.toLong().toString()
    } else {
        value.toString()
    }
}