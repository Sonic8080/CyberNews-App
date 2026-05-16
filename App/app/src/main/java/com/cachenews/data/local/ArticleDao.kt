package com.cachenews.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE category = :category ORDER BY publishedAt DESC")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    @Query("""
        SELECT * FROM articles 
        WHERE category = :category 
        AND publishedAt >= :fromTimestamp 
        ORDER BY publishedAt DESC
    """)
    fun getArticlesByCategoryAndTime(category: String, fromTimestamp: Long): Flow<List<ArticleEntity>>

    @Query("""
        SELECT * FROM articles 
        WHERE category = :category 
        AND sourceName = :sourceName 
        AND publishedAt >= :fromTimestamp 
        ORDER BY publishedAt DESC
    """)
    fun getArticlesByCategorySourceAndTime(
        category: String, 
        sourceName: String, 
        fromTimestamp: Long
    ): Flow<List<ArticleEntity>>

    @Query("""
        SELECT * FROM articles 
        WHERE category = :category 
        AND sourceName = :sourceName 
        ORDER BY publishedAt DESC
    """)
    fun getArticlesByCategoryAndSource(category: String, sourceName: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: String): ArticleEntity?

    @Query("SELECT * FROM articles WHERE isBookmarked = 1 ORDER BY publishedAt DESC")
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE isNotified = 0 ORDER BY publishedAt DESC LIMIT 20")
    suspend fun getUnnotifiedArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>): List<Long>

    @Update
    suspend fun updateArticle(article: ArticleEntity)

    @Query("UPDATE articles SET isBookmarked = :bookmarked WHERE id = :id")
    suspend fun setBookmarked(id: String, bookmarked: Boolean)

    @Query("UPDATE articles SET isNotified = 1 WHERE id IN (:ids)")
    suspend fun markAsNotified(ids: List<String>)

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE sourceUrl = :url)")
    suspend fun articleExists(url: String): Boolean

    @Query("SELECT COUNT(*) FROM articles WHERE category = :category")
    suspend fun getArticleCount(category: String): Int

    @Query("""
        SELECT * FROM articles 
        WHERE category = :category 
        AND (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY publishedAt DESC
    """)
    fun searchArticles(category: String, query: String): Flow<List<ArticleEntity>>

    @Query("""
        SELECT * FROM articles 
        WHERE isBookmarked = 1
        AND (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY publishedAt DESC
    """)
    fun searchBookmarkedArticles(query: String): Flow<List<ArticleEntity>>

    /** Delete non-bookmarked articles older than the given timestamp */
    @Query("DELETE FROM articles WHERE isBookmarked = 0 AND publishedAt < :beforeTimestamp")
    suspend fun deleteOldArticles(beforeTimestamp: Long)

    /** Get count of all articles */
    @Query("SELECT COUNT(*) FROM articles")
    suspend fun getTotalArticleCount(): Int

    /** Delete all non-bookmarked articles (for full cache clear) */
    @Query("DELETE FROM articles WHERE isBookmarked = 0")
    suspend fun clearNonBookmarkedArticles()
}
