package com.cachenews.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles",
    indices = [
        Index(value = ["sourceUrl"], unique = true),
        Index(value = ["category"]),
        Index(value = ["sourceName"]),
        Index(value = ["publishedAt"])
    ]
)
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String?,
    val sourceUrl: String,
    val sourceName: String,
    val source: String,
    val category: String,
    val publishedAt: Long,
    val isBookmarked: Boolean = false,
    val isNotified: Boolean = false
)

@Entity(
    tableName = "translation_cache",
    indices = [Index(value = ["textHash", "targetLanguage"], unique = true)]
)
data class TranslationCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val textHash: String,
    val originalText: String,
    val translatedText: String,
    val targetLanguage: String,
    val createdAt: Long = System.currentTimeMillis()
)
