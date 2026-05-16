package com.cachenews.ui.detail

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cachenews.domain.model.AppLanguage
import com.cachenews.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            DetailTopBar(
                onBackClick = onBackClick,
                onBookmark = { viewModel.toggleBookmark() },
                isBookmarked = uiState.article?.isBookmarked ?: false,
                onShare = {
                    uiState.article?.let { article ->
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "${article.title}\n${article.sourceUrl}")
                        }
                        context.startActivity(Intent.createChooser(intent, "Share"))
                    }
                },
                onOpenBrowser = {
                    uiState.article?.sourceUrl?.let { url ->
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                }
            )
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = NeonBlue)
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.error ?: "Error", color = ErrorRed)
                }
            }
            uiState.article != null -> {
                val article = uiState.article!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                ) {
                    // Translation bar (independent of global language)
                    TranslateBar(
                        currentLanguage = uiState.detailLanguage,
                        isTranslating = uiState.isTranslating,
                        onLanguageSelected = { viewModel.setDetailLanguage(it) }
                    )

                    // Hero image
                    if (!article.imageUrl.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            AsyncImage(
                                model = article.imageUrl,
                                contentDescription = article.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            // Gradient overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                DarkBackground.copy(alpha = 0.8f),
                                                DarkBackground
                                            ),
                                            startY = 100f
                                        )
                                    )
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        // Source info
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(NeonBlue.copy(alpha = 0.3f), NeonPurple.copy(alpha = 0.3f))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = article.sourceName.take(1),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = NeonBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column {
                                Text(
                                    text = article.sourceName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
                                        .format(Date(article.publishedAt)),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextTertiary
                                )
                            }
                        }

                        // Title
                        Text(
                            text = article.translatedTitle ?: article.title,
                            style = MaterialTheme.typography.headlineLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        // Description
                        val desc = article.translatedDescription ?: article.description
                        if (desc.isNotBlank()) {
                            Text(
                                text = desc,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondary,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Divider(
                            color = DarkSurfaceVariant,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        // Full content
                        val displayContent = uiState.translatedContent ?: uiState.fullContent
                        if (displayContent.isNotBlank()) {
                            if (displayContent.contains("<") && displayContent.contains(">")) {
                                // HTML content - use WebView
                                val styledHtml = """
                                    <html>
                                    <head>
                                    <style>
                                        body { 
                                            color: #E8EAED; 
                                            background: #0A0E21; 
                                            font-family: sans-serif;
                                            font-size: 16px;
                                            line-height: 1.7;
                                            padding: 0 4px;
                                            word-wrap: break-word;
                                        }
                                        img { 
                                            max-width: 100%; 
                                            height: auto; 
                                            border-radius: 12px;
                                            margin: 12px 0;
                                        }
                                        a { color: #00D4FF; }
                                        h1, h2, h3 { color: #E8EAED; }
                                        p { margin: 8px 0; }
                                        pre, code {
                                            background: #1A1F3C;
                                            padding: 8px;
                                            border-radius: 8px;
                                            overflow-x: auto;
                                            font-size: 14px;
                                        }
                                    </style>
                                    </head>
                                    <body>$displayContent</body>
                                    </html>
                                """.trimIndent()

                                AndroidView(
                                    factory = { ctx ->
                                        WebView(ctx).apply {
                                            webViewClient = WebViewClient()
                                            setBackgroundColor(android.graphics.Color.TRANSPARENT)
                                            settings.apply {
                                                javaScriptEnabled = false
                                                loadWithOverviewMode = true
                                                useWideViewPort = true
                                            }
                                            loadDataWithBaseURL(
                                                null, styledHtml, "text/html", "UTF-8", null
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 300.dp)
                                )
                            } else {
                                // Plain text
                                Text(
                                    text = displayContent,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextPrimary.copy(alpha = 0.9f),
                                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                                )
                            }
                        } else if (uiState.isTranslating) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(
                                        color = NeonBlue,
                                        modifier = Modifier.size(32.dp),
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text("Translating…", color = TextSecondary)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Open original button
                        OutlinedButton(
                            onClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, Uri.parse(article.sourceUrl))
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = NeonBlue
                            ),
                            border = BorderStroke(1.dp, NeonBlue.copy(alpha = 0.3f))
                        ) {
                            Icon(
                                Icons.Filled.OpenInBrowser,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Open Original Source")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    onBackClick: () -> Unit,
    onBookmark: () -> Unit,
    isBookmarked: Boolean,
    onShare: () -> Unit,
    onOpenBrowser: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = onBookmark) {
                Icon(
                    if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) NeonBlue else TextSecondary
                )
            }
            IconButton(onClick = onShare) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = TextSecondary)
            }
            IconButton(onClick = onOpenBrowser) {
                Icon(Icons.Filled.OpenInBrowser, contentDescription = "Open", tint = TextSecondary)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBackground.copy(alpha = 0.95f)
        )
    )
}

@Composable
private fun TranslateBar(
    currentLanguage: AppLanguage,
    isTranslating: Boolean,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Filled.Translate,
                    contentDescription = null,
                    tint = NeonBlue,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Article Language",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondary
                )
                if (isTranslating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = NeonBlue,
                        strokeWidth = 2.dp
                    )
                }
            }

            Box {
                SuggestionChip(
                    onClick = { expanded = true },
                    label = {
                        Text(
                            currentLanguage.nativeName,
                            color = NeonBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Language,
                            contentDescription = null,
                            tint = NeonBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = NeonBlue.copy(alpha = 0.1f)
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        borderColor = NeonBlue.copy(alpha = 0.3f),
                        enabled = true
                    )
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = DarkCard
                ) {
                    AppLanguage.entries.forEach { language ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        language.nativeName,
                                        color = if (language == currentLanguage) NeonBlue else TextPrimary,
                                        fontWeight = if (language == currentLanguage) FontWeight.Bold else FontWeight.Normal
                                    )
                                    if (language == currentLanguage) {
                                        Icon(
                                            Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = NeonBlue,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                onLanguageSelected(language)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
