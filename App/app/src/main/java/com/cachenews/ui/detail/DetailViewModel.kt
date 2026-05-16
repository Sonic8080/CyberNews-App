package com.cachenews.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cachenews.data.repository.NewsRepository
import com.cachenews.data.repository.SettingsRepository
import com.cachenews.data.repository.TranslationRepository
import com.cachenews.domain.model.AppLanguage
import com.cachenews.domain.model.Article
import com.cachenews.ui.feed.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val article: Article? = null,
    val fullContent: String = "",
    val translatedContent: String? = null,
    val isLoading: Boolean = true,
    val isTranslating: Boolean = false,
    val globalLanguage: AppLanguage = AppLanguage.ENGLISH,
    val detailLanguage: AppLanguage = AppLanguage.ENGLISH, // Independent from global
    val error: String? = null
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val translationRepository: TranslationRepository,
    private val settingsRepository: SettingsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleId = savedStateHandle.get<String>("articleId") ?: ""

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadArticle()
        observeLanguage()
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            settingsRepository.selectedLanguage.collect { lang ->
                _uiState.update { it.copy(globalLanguage = lang, detailLanguage = lang) }
            }
        }
    }

    private fun loadArticle() {
        viewModelScope.launch {
            try {
                val entity = newsRepository.getArticleById(articleId) ?: run {
                    _uiState.update { it.copy(isLoading = false, error = "Article not found") }
                    return@launch
                }

                val article = entity.toDomain()
                _uiState.update { it.copy(article = article, isLoading = false) }

                // Fetch full content if needed
                if (article.content.isBlank() || article.content.length < 200) {
                    val content = newsRepository.fetchArticleContent(article.sourceUrl)
                    if (content.isNotBlank()) {
                        _uiState.update { it.copy(fullContent = content) }
                    }
                } else {
                    _uiState.update { it.copy(fullContent = article.content) }
                }

                // Translate with global language
                translateCurrentContent()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    /**
     * Change language for this detail page ONLY (independent of global).
     */
    fun setDetailLanguage(language: AppLanguage) {
        _uiState.update { it.copy(detailLanguage = language) }
        translateCurrentContent()
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            val article = _uiState.value.article ?: return@launch
            newsRepository.toggleBookmark(article.id, !article.isBookmarked)
            _uiState.update {
                it.copy(article = article.copy(isBookmarked = !article.isBookmarked))
            }
        }
    }

    private fun translateCurrentContent() {
        viewModelScope.launch {
            val state = _uiState.value
            val article = state.article ?: return@launch
            val lang = state.detailLanguage

            _uiState.update { it.copy(isTranslating = true) }

            try {
                // Translate article fields
                val translated = translationRepository.translateArticle(article, lang)

                // Translate full content
                val sourceLang = if (article.sourceName == "Anti-Malware") "ru" else "en"
                val translatedContent = if (state.fullContent.isNotBlank()) {
                    translationRepository.translateContent(state.fullContent, lang, sourceLang)
                } else null

                _uiState.update {
                    it.copy(
                        article = translated,
                        translatedContent = translatedContent,
                        isTranslating = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isTranslating = false) }
            }
        }
    }
}
