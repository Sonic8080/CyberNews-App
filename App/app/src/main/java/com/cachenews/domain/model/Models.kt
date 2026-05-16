package com.cachenews.domain.model

enum class NewsCategory(val displayName: String) {
    CYBER("Cyber Security"),
    AI("Artificial Intelligence"),
    SAVED("Saved Articles")
}

enum class NewsSource(val displayName: String, val category: NewsCategory) {
    THE_HACKER_NEWS("The Hacker News", NewsCategory.CYBER),
    CYBER_PRESS("Cyber Press", NewsCategory.CYBER),
    HACK_READ("HackRead", NewsCategory.CYBER),
    BLEEPING_COMPUTER("BleepingComputer", NewsCategory.CYBER),
    EVOLVING_AI("Evolving AI", NewsCategory.AI),
    HUGGING_FACE("Hugging Face", NewsCategory.AI)
}

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String?,
    val sourceUrl: String,
    val sourceName: String,
    val source: NewsSource,
    val category: NewsCategory,
    val publishedAt: Long,
    val isBookmarked: Boolean = false,
    val translatedTitle: String? = null,
    val translatedDescription: String? = null,
    val translatedContent: String? = null
)

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    ENGLISH("en", "English", "English"),
    TURKISH("tr", "Turkish", "Türkçe"),
    AZERBAIJANI("az", "Azerbaijani", "Azərbaycan")
}

enum class TimeFilter(val displayName: String) {
    LATEST("Latest"),
    TODAY("Today"),
    THIS_WEEK("This Week"),
    THIS_MONTH("This Month")
}
