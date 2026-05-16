package com.cachenews.ui.start

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cachenews.domain.model.NewsCategory
import com.cachenews.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    onCategorySelected: (NewsCategory) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: StartViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        AnimatedBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with Settings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "CacheNews",
                        style = MaterialTheme.typography.displayLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp
                    )
                    Text(
                        text = "Stay informed. Stay ahead.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }

                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(CircleShape)
                        .background(DarkSurfaceVariant)
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = TextPrimary
                    )
                }
            }

            // Category cards
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            ) {
                CategoryCard(
                    title = "Cyber Security",
                    subtitle = "Latest threats & vulnerabilities",
                    icon = Icons.Filled.Security,
                    gradientColors = listOf(GradientCyberStart, GradientCyberEnd),
                    onClick = { onCategorySelected(NewsCategory.CYBER) }
                )

                CategoryCard(
                    title = "Artificial Intelligence",
                    subtitle = "AI breakthroughs & research",
                    icon = Icons.Filled.Psychology,
                    gradientColors = listOf(GradientAIStart, GradientAIEnd),
                    onClick = { onCategorySelected(NewsCategory.AI) }
                )

                CategoryCard(
                    title = "Saved Articles",
                    subtitle = "Your bookmarked news",
                    icon = Icons.Filled.Bookmark,
                    gradientColors = listOf(NeonBlue, NeonPurple),
                    onClick = { onCategorySelected(NewsCategory.SAVED) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CategoryCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "card_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .scale(scale)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = gradientColors[0].copy(alpha = 0.2f),
                spotColor = gradientColors[1].copy(alpha = 0.2f)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                pressed = true
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = gradientColors.map { it.copy(alpha = 0.15f) }
                    )
                )
                .background(DarkCard.copy(alpha = 0.7f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(gradientColors.map { it.copy(alpha = 0.2f) })
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = gradientColors[0],
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")

    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (100 * offset1).dp, y = (50 * offset1).dp)
                .blur(120.dp)
                .clip(CircleShape)
                .background(NeonBlue.copy(alpha = 0.05f))
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (50 * (1-offset1)).dp, y = (-50 * (1-offset1)).dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(NeonPurple.copy(alpha = 0.05f))
        )
    }
}
