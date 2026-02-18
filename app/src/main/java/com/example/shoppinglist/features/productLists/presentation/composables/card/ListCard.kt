package com.example.shoppinglist.features.productLists.presentation.composables.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.presentation.ui.components.SlButtons
import com.example.shoppinglist.core.presentation.ui.components.SlIcon
import com.example.shoppinglist.core.presentation.ui.components.SwipeCardController
import com.example.shoppinglist.core.presentation.ui.components.SwipeContainer
import com.example.shoppinglist.core.theme.AppDimens
import com.example.shoppinglist.features.productLists.domain.entity.ProductList
import com.example.shoppinglist.features.productLists.presentation.ProductListsViewModel
import com.example.shoppinglist.features.productLists.presentation.model.ProductListsEvent

@Composable
fun ListCard(
    list: ProductList,
    viewModel: ProductListsViewModel,
    controller: SwipeCardController,
    onItemClick: (ProductList) -> Unit
) {
    SwipeContainer(
        id = list.id,
        maxOffset = 350.dp,
        controller = controller,
        mainBackground = {
            CardActionButtons(viewModel, list)
        },
        extendedBackground = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SlButtons.SlIcon(
                    onClick = {
                        viewModel.obtainEvent(
                            ProductListsEvent.BtnDeleteInClick(
                                list
                            )
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    painter = painterResource(id = R.drawable.trash),
                )
            }
        },
        content = {
            Card(
                onClick = { onItemClick(list) },
                colors = CardDefaults.cardColors(
                    contentColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 3.dp,
                ),
                modifier = Modifier
                    .height(AppDimens.HeightBase)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        if (list.icon != null)
                            Icon(
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                painter = painterResource(R.drawable.plus),
                                contentDescription = null,
                            )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        list.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    )
}
