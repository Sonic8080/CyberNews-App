package com.cachenews.ui.feed

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cachenews.data.local.ArticleEntity
import com.cachenews.data.repository.NewsRepository
import com.cachenews.data.repository.SettingsRepository
import com.cachenews.data.repository.TranslationRepository
import com.cachenews.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val selectedTimeFilter: TimeFilter = TimeFilter.LATEST,
    val selectedSourceFilter: NewsSource? = null,
    val category: NewsCategory = NewsCategory.CYBER,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val searchQuery: String = "",
    val isSearchActive: Boolean = false
)

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val translationRepository: TranslationRepository,
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryArg = savedStateHandle.get<String>("category") ?: "CYBER"

    private val _uiState = MutableStateFlow(
        FeedUiState(category = NewsCategory.valueOf(categoryArg))
    )
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        observeLanguage()
        loadArticles()
        refreshFromNetwork()
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            settingsRepository.selectedLanguage.collect { lang ->
                _uiState.update { it.copy(language = lang) }
                // Re-translate visible articles
                retranslateArticles(lang)
            }
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            val state = _uiState.value
            newsRepository.getArticles(
                category = state.category,
                timeFilter = state.selectedTimeFilter,
                sourceFilter = state.selectedSourceFilter
            ).collect { entities ->
                val articles = entities.map { it.toDomain() }
                val lang = _uiState.value.language
                val translated = translateArticles(articles, lang)
                _uiState.update {
                    it.copy(articles = translated, isLoading = false, error = null)
                }
            }
        }
    }

    fun refreshFromNetwork() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            val result = newsRepository.refreshArticles(_uiState.value.category)
            _uiState.update {
                it.copy(
                    isRefreshing = false,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun setTimeFilter(filter: TimeFilter) {
        _uiState.update { it.copy(selectedTimeFilter = filter, isLoading = true) }
        loadArticles()
    }

    fun setSourceFilter(source: NewsSource?) {
        _uiState.update { it.copy(selectedSourceFilter = source, isLoading = true) }
        loadArticles()
    }

    fun toggleBookmark(articleId: String) {
        viewModelScope.launch {
            val article = _uiState.value.articles.find { it.id == articleId } ?: return@launch
            newsRepository.toggleBookmark(articleId, !article.isBookmarked)
        }
    }

    fun shareArticle(article: Article) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, article.title)
            putExtra(Intent.EXTRA_TEXT, "${article.title}\n\n${article.sourceUrl}")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(Intent.createChooser(intent, "Share article").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isBlank()) {
            loadArticles()
        } else {
            viewModelScope.launch {
                newsRepository.searchArticles(_uiState.value.category, query).collect { entities ->
                    val articles = entities.map { it.toDomain() }
                    val translated = translateArticles(articles, _uiState.value.language)
                    _uiState.update { it.copy(articles = translated) }
                }
            }
        }
    }

    fun toggleSearch() {
        _uiState.update {
            it.copy(isSearchActive = !it.isSearchActive, searchQuery = "")
        }
        if (!_uiState.value.isSearchActive) loadArticles()
    }

    private suspend fun translateArticles(articles: List<Article>, lang: AppLanguage): List<Article> {
        return articles.map { article ->
            try {
                translationRepository.translateArticle(article, lang)
            } catch (e: Exception) {
                article
            }
        }
    }

    private fun retranslateArticles(lang: AppLanguage) {
        viewModelScope.launch {
            val current = _uiState.value.articles
            val translated = translateArticles(current, lang)
            _uiState.update { it.copy(articles = translated) }
        }
    }
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        sourceUrl = sourceUrl,
        sourceName = sourceName,
        source = try { NewsSource.valueOf(source) } catch (_: Exception) { NewsSource.THE_HACKER_NEWS },
        category = try { NewsCategory.valueOf(category) } catch (_: Exception) { NewsCategory.CYBER },
        publishedAt = publishedAt,
        isBookmarked = isBookmarked
    )
}
