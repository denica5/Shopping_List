package com.example.shoppinglist.core.presentation.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun <T> SwipeContainer(
    id: T,
    controller: SwipeCardController,
    maxOffset: Dp = 240.dp,
    modifier: Modifier = Modifier,
    background1: @Composable BoxScope.() -> Unit,
    background2: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val maxOffsetPx = with(density) { maxOffset.toPx() }

    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(controller.activeCardId) {
        if (controller.activeCardId != id) {
            offsetX.animateTo(0f)
        }
    }

    Box(modifier = modifier) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    alpha = if (offsetX.value <= -maxOffsetPx * 0.7f) {
                        1f
                    } else {
                        0f
                    }
                }
        ) {
            background2()
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {

                    alpha = if (offsetX.value <= -maxOffsetPx * 0.7f) {
                        0f
                    } else {
                        1f
                    }
                }
        ) {
            background1()
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .pointerInput(id) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            controller.activeCardId = id
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = (offsetX.value + dragAmount)
                                .coerceIn(-maxOffsetPx, 0f)

                            scope.launch {
                                offsetX.snapTo(newOffset)
                            }
                        },

                        onDragEnd = {
                            val anchors = listOf(
                                0f,
                                -maxOffsetPx / 2,
                                -maxOffsetPx * 0.9f
                            )

                            val nearest = anchors.minByOrNull {
                                abs(it - offsetX.value)
                            } ?: 0f

                            scope.launch {
                                offsetX.animateTo(nearest)
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}
