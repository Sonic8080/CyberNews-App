package com.cachenews.data.repository

import com.cachenews.data.local.ArticleDao
import com.cachenews.data.local.ArticleEntity
import com.cachenews.data.remote.RssParser
import com.cachenews.data.remote.RssService
import com.cachenews.domain.model.NewsCategory
import com.cachenews.domain.model.NewsSource
import com.cachenews.domain.model.TimeFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val rssService: RssService,
    private val rssParser: RssParser
) {
    companion object {
        private val CYBER_FEED_URLS = mapOf(
            NewsSource.THE_HACKER_NEWS to "https://feeds.feedburner.com/TheHackersNews",
            NewsSource.CYBER_PRESS to "https://cyberpress.org/feed/",
            NewsSource.HACK_READ to "https://hackread.com/feed/",
            NewsSource.BLEEPING_COMPUTER to "https://www.bleepingcomputer.com/feed/"
        )

        private val AI_FEED_URLS = mapOf(
            NewsSource.EVOLVING_AI to "https://evolvingai.io/feed",
            NewsSource.HUGGING_FACE to "https://huggingface.co/blog/feed.xml"
        )

        private const val STALE_THRESHOLD_MS = 24L * 60 * 60 * 1000 // 1 day for aggressive fresh data
    }

    fun getArticles(
        category: NewsCategory,
        timeFilter: TimeFilter = TimeFilter.LATEST,
        sourceFilter: NewsSource? = null
    ): Flow<List<ArticleEntity>> {
        if (category == NewsCategory.SAVED) {
            return articleDao.getBookmarkedArticles()
        }
        
        val catStr = category.name
        val fromTimestamp = getTimestampForFilter(timeFilter)

        return when {
            sourceFilter != null && timeFilter != TimeFilter.LATEST ->
                articleDao.getArticlesByCategorySourceAndTime(catStr, sourceFilter.displayName, fromTimestamp)
            sourceFilter != null ->
                articleDao.getArticlesByCategoryAndSource(catStr, sourceFilter.displayName)
            timeFilter != TimeFilter.LATEST ->
                articleDao.getArticlesByCategoryAndTime(catStr, fromTimestamp)
            else ->
                articleDao.getArticlesByCategory(catStr)
        }
    }

    suspend fun getArticleById(id: String): ArticleEntity? {
        return articleDao.getArticleById(id)
    }

    suspend fun refreshArticles(category: NewsCategory): Result<Int> = withContext(Dispatchers.IO) {
        if (category == NewsCategory.SAVED) return@withContext Result.success(0)
        try {
            val articles = when (category) {
                NewsCategory.CYBER -> fetchCyberArticles()
                NewsCategory.AI -> fetchAIArticles()
                else -> emptyList()
            }
            val insertedCount = articleDao.insertArticles(articles)
            Result.success(insertedCount.count { it != -1L })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refreshAllArticles(): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val allArticles = coroutineScope {
                val cyberDeferred = async { fetchCyberArticles() }
                val aiDeferred = async { fetchAIArticles() }
                cyberDeferred.await() + aiDeferred.await()
            }
            val insertedCount = articleDao.insertArticles(allArticles)
            Result.success(insertedCount.count { it != -1L })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun cleanStaleCache() = withContext(Dispatchers.IO) {
        try {
            // Force clear non-bookmarked cache to ensure dynamic updates as requested
            val threshold = System.currentTimeMillis() - STALE_THRESHOLD_MS
            articleDao.deleteOldArticles(threshold)
        } catch (_: Exception) { }
    }

    suspend fun getUnnotifiedArticles(): List<ArticleEntity> {
        return articleDao.getUnnotifiedArticles()
    }

    suspend fun markAsNotified(ids: List<String>) {
        articleDao.markAsNotified(ids)
    }

    suspend fun toggleBookmark(id: String, bookmarked: Boolean) {
        articleDao.setBookmarked(id, bookmarked)
    }

    fun getBookmarkedArticles(): Flow<List<ArticleEntity>> {
        return articleDao.getBookmarkedArticles()
    }

    fun searchArticles(category: NewsCategory, query: String): Flow<List<ArticleEntity>> {
        return if (category == NewsCategory.SAVED) {
            articleDao.searchBookmarkedArticles(query)
        } else {
            articleDao.searchArticles(category.name, query)
        }
    }

    suspend fun fetchArticleContent(url: String): String {
        return rssParser.scrapeArticleContent(url)
    }

    private suspend fun fetchCyberArticles(): List<ArticleEntity> = coroutineScope {
        CYBER_FEED_URLS.map { (source, url) ->
            async {
                try {
                    val response = rssService.fetchFeed(url)
                    val xml = response.string()
                    rssParser.parseRssFeed(xml, source)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }.awaitAll().flatten()
    }

    private suspend fun fetchAIArticles(): List<ArticleEntity> = coroutineScope {
        AI_FEED_URLS.map { (source, url) ->
            async {
                try {
                    val response = rssService.fetchFeed(url)
                    val xml = response.string()
                    val articles = rssParser.parseRssFeed(xml, source)
                    if (articles.isNotEmpty()) articles
                    else if (source == NewsSource.EVOLVING_AI) rssParser.scrapeEvolvingAI()
                    else emptyList()
                } catch (e: Exception) {
                    if (source == NewsSource.EVOLVING_AI) {
                        try { rssParser.scrapeEvolvingAI() } catch (_: Exception) { emptyList() }
                    } else emptyList()
                }
            }
        }.awaitAll().flatten()
    }

    private fun getTimestampForFilter(filter: TimeFilter): Long {
        val calendar = Calendar.getInstance()
        return when (filter) {
            TimeFilter.LATEST -> 0L
            TimeFilter.TODAY -> {
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.timeInMillis
            }
            TimeFilter.THIS_WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.timeInMillis
            }
            TimeFilter.THIS_MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.timeInMillis
            }
        }
    }
}
