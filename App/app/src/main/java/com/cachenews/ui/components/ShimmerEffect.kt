package com.cachenews.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cachenews.ui.theme.DarkCard
import com.cachenews.ui.theme.DarkCardElevated

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        DarkCard,
        DarkCardElevated.copy(alpha = 0.7f),
        DarkCard
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value - 200, translateAnim.value - 200),
        end = Offset(translateAnim.value, translateAnim.value)
    )

    Column(modifier = modifier.padding(16.dp)) {
        repeat(3) {
            ShimmerCard(brush = brush)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ShimmerCard(brush: Brush) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkCard)
            .padding(16.dp)
    ) {
        // Image placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Title placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Description placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
    }
}
