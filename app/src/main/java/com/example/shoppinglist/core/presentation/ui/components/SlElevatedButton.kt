package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.core.theme.ShoppingListTheme

@Stable
@Composable
fun SlButtons.SlElevatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(16.dp),
    iconPainter: Painter,
) {
//    Surface(
//        modifier = modifier,
//        shape = shape,
//        color = MaterialTheme.colorScheme.primaryContainer,
//        shadowElevation = 3.dp,
//        tonalElevation = 0.dp,
//        onClick = onClick
//    ) {
//        Box(
//            modifier = Modifier.size(56.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = imageVector,
//                contentDescription = "",
//                tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        elevation = FloatingActionButtonDefaults.elevation(3.dp)
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(24.dp)
        )
    }

}

@Preview
@Composable
fun PreviewButtonSl() {
    ShoppingListTheme(dynamicColor = false) {
        Box(){
            SlButtons.SlElevatedButton(
                modifier = Modifier.size(56.dp), {}, RoundedCornerShape(16.dp),
                painterResource(R.drawable.plus)
            )
        }
    }
}
