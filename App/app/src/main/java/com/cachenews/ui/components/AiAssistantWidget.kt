package com.cachenews.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.cachenews.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun FloatingAiAssistant(
    articleContext: String? = null,
    articleTitle: String? = null,
    onCloseContext: () -> Unit = {}
) {
    var showChat by remember { mutableStateOf(false) }

    // Automatically open chat when context is provided (from "Ask AI" button)
    LaunchedEffect(articleContext) {
        if (articleContext != null) {
            showChat = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Floating Button
        if (!showChat) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .size(60.dp)
                    .shadow(12.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(NeonBlue, NeonPurple)))
                    .clickable { showChat = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.AutoAwesome,
                    contentDescription = "AI Assistant",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Centered Modal Chat interface
        if (showChat) {
            Dialog(
                onDismissRequest = {
                    showChat = false
                    onCloseContext()
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                AiChatInterface(
                    articleContext = articleContext,
                    articleTitle = articleTitle,
                    onClose = {
                        showChat = false
                        onCloseContext()
                    }
                )
            }
        }
    }
}

@Composable
private fun AiChatInterface(
    articleContext: String?,
    articleTitle: String?,
    onClose: () -> Unit,
    viewModel: AiAssistantViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(articleContext, articleTitle) {
        if (articleContext != null && articleTitle != null) {
            viewModel.setContext(articleTitle, articleContext)
        }
    }

    // Scroll to bottom when new messages arrive
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .fillMaxHeight(0.8f)
            .shadow(24.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = DarkSurface
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(NeonBlue.copy(alpha = 0.15f), NeonPurple.copy(alpha = 0.15f))))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = NeonBlue)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("CyberNews Assistant", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 16.sp)
                        if (uiState.articleTitle != null) {
                            Text(
                                "Context: ${uiState.articleTitle}",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonBlue,
                                maxLines = 1
                            )
                        }
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            // Messages
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                reverseLayout = true,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                if (uiState.isTyping) {
                    item {
                        Box(modifier = Modifier.padding(bottom = 8.dp)) {
                            Text(
                                "CyberNews Assistant is typing...",
                                color = NeonBlue.copy(alpha = 0.7f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                items(uiState.messages.reversed()) { message ->
                    val isUser = message.role == "user"
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                    ) {
                        Surface(
                            color = if (isUser) NeonBlue.copy(alpha = 0.2f) else DarkSurfaceVariant,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (isUser) 16.dp else 4.dp,
                                bottomEnd = if (isUser) 4.dp else 16.dp
                            ),
                            border = if (!isUser) null else BorderStroke(1.dp, NeonBlue.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = message.content,
                                modifier = Modifier.padding(12.dp),
                                color = TextPrimary,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                        Text(
                            text = if (isUser) "You" else "CyberNews Assistant",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Input
            var textState by remember { mutableStateOf("") }
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = DarkSurfaceVariant.copy(alpha = 0.5f),
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextField(
                        value = textState,
                        onValueChange = { textState = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Ask about security, AI, or this article...", fontSize = 14.sp, color = TextTertiary) },
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = DarkSurface,
                            unfocusedContainerColor = DarkSurface,
                            focusedIndicatorColor = NeonBlue,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        maxLines = 4
                    )
                    FloatingActionButton(
                        onClick = {
                            if (textState.isNotBlank()) {
                                viewModel.sendMessage(textState)
                                textState = ""
                            }
                        },
                        modifier = Modifier.size(48.dp),
                        containerColor = NeonBlue,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Send, contentDescription = "Send", modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}
