package com.cachenews.ui.feed

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cachenews.domain.model.*
import com.cachenews.ui.components.ArticleCard
import com.cachenews.ui.components.FloatingAiAssistant
import com.cachenews.ui.components.ShimmerEffect
import com.cachenews.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    category: NewsCategory,
    onArticleClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // State for article-specific AI context
    var aiArticleContext by remember { mutableStateOf<Article?>(null) }

    Scaffold(
        topBar = {
            FeedTopBar(
                category = category,
                isSearchActive = uiState.isSearchActive,
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { viewModel.setSearchQuery(it) },
                onToggleSearch = { viewModel.toggleSearch() },
                onBackClick = onBackClick
            )
        },
        containerColor = DarkBackground,
        floatingActionButton = {
            // Persistent AI Assistant at bottom-right
            FloatingAiAssistant(
                articleTitle = aiArticleContext?.title,
                articleContext = aiArticleContext?.content,
                onCloseContext = { aiArticleContext = null }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter bar (Hide for SAVED category)
            if (category != NewsCategory.SAVED) {
                FilterBar(
                    category = category,
                    selectedTimeFilter = uiState.selectedTimeFilter,
                    selectedSourceFilter = uiState.selectedSourceFilter,
                    onTimeFilterSelected = { viewModel.setTimeFilter(it) },
                    onSourceFilterSelected = { viewModel.setSourceFilter(it) }
                )
            }

            // Content
            when {
                uiState.isLoading -> ShimmerEffect(modifier = Modifier.fillMaxSize())
                uiState.articles.isEmpty() -> EmptyState(category)
                else -> {
                    PullToRefreshBox(
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = { viewModel.refreshFromNetwork() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            state = listState,
                            contentPadding = PaddingValues(bottom = 80.dp), // Space for FAB
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = uiState.articles,
                                key = { it.id }
                            ) { article ->
                                ArticleCard(
                                    article = article,
                                    onClick = { onArticleClick(article.id) },
                                    onBookmarkClick = { viewModel.toggleBookmark(article.id) },
                                    onShareClick = { viewModel.shareArticle(article) },
                                    onAskAiClick = { aiArticleContext = article }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedTopBar(
    category: NewsCategory,
    isSearchActive: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            if (isSearchActive) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search articles…", color = TextTertiary) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceVariant,
                        unfocusedContainerColor = DarkSurfaceVariant,
                        focusedTextColor = TextPrimary,
                        cursorColor = NeonBlue,
                        focusedIndicatorColor = NeonBlue
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = category.displayName,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        },
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
            IconButton(onClick = onToggleSearch) {
                Icon(
                    if (isSearchActive) Icons.Filled.Close else Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = NeonBlue
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBackground,
            titleContentColor = TextPrimary
        )
    )
}

@Composable
private fun FilterBar(
    category: NewsCategory,
    selectedTimeFilter: TimeFilter,
    selectedSourceFilter: NewsSource?,
    onTimeFilterSelected: (TimeFilter) -> Unit,
    onSourceFilterSelected: (NewsSource?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TimeFilter.entries.forEach { filter ->
                FilterChip(
                    selected = selectedTimeFilter == filter,
                    onClick = { onTimeFilterSelected(filter) },
                    label = {
                        Text(
                            filter.displayName,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = DarkSurfaceVariant,
                        selectedContainerColor = NeonBlue.copy(alpha = 0.2f),
                        labelColor = TextSecondary,
                        selectedLabelColor = NeonBlue
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        if (category == NewsCategory.CYBER) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedSourceFilter == null,
                    onClick = { onSourceFilterSelected(null) },
                    label = { Text("All Sources") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = DarkSurfaceVariant,
                        selectedContainerColor = NeonPurple.copy(alpha = 0.2f),
                        labelColor = TextSecondary,
                        selectedLabelColor = NeonPurple
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                NewsSource.entries.filter { it.category == NewsCategory.CYBER }.forEach { source ->
                    FilterChip(
                        selected = selectedSourceFilter == source,
                        onClick = { onSourceFilterSelected(source) },
                        label = { Text(source.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = DarkSurfaceVariant,
                            selectedContainerColor = NeonPurple.copy(alpha = 0.2f),
                            labelColor = TextSecondary,
                            selectedLabelColor = NeonPurple
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyState(category: NewsCategory) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (category == NewsCategory.SAVED) "No saved articles yet" else "No articles found",
                style = MaterialTheme.typography.titleLarge,
                color = TextSecondary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (category == NewsCategory.SAVED) "Bookmark articles to see them here" else "Pull down to refresh",
                style = MaterialTheme.typography.bodyMedium,
                color = TextTertiary
            )
        }
    }
}
