package com.cachenews.data.repository

import com.cachenews.data.translate.TranslationService
import com.cachenews.domain.model.AppLanguage
import com.cachenews.domain.model.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationRepository @Inject constructor(
    private val translationService: TranslationService,
    private val settingsRepository: SettingsRepository
) {
    /**
     * Translate an article's display fields to the given language.
     * Called only when user explicitly taps the Translate button.
     */
    suspend fun translateArticle(article: Article, targetLang: AppLanguage): Article {
        if (targetLang == AppLanguage.ENGLISH) {
            // Most sources are in English, skip translation
            return article
        }

        val sourceLang = "en"
        val targetCode = targetLang.code

        if (sourceLang == targetCode) return article

        val translatedTitle = translationService.translate(article.title, sourceLang, targetCode)
        val translatedDesc = translationService.translate(article.description, sourceLang, targetCode)

        return article.copy(
            translatedTitle = translatedTitle ?: article.title,
            translatedDescription = translatedDesc ?: article.description
        )
    }

    /**
     * Translate full article content for the detail page.
     */
    suspend fun translateContent(content: String, targetLang: AppLanguage, sourceLang: String = "en"): String {
        if (content.isBlank()) return content
        if (sourceLang == targetLang.code) return content

        return translationService.translate(content, sourceLang, targetLang.code) ?: content
    }
}
