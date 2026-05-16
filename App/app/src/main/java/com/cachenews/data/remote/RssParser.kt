package com.cachenews.data.remote

import com.cachenews.data.local.ArticleEntity
import com.cachenews.domain.model.NewsCategory
import com.cachenews.domain.model.NewsSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RssParser @Inject constructor() {

    private val dateFormats = listOf(
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH),
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH),
        SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    ).onEach { it.timeZone = TimeZone.getTimeZone("UTC") }

    suspend fun parseRssFeed(xml: String, source: NewsSource): List<ArticleEntity> = withContext(Dispatchers.Default) {
        try {
            val doc = Jsoup.parse(xml, "", Parser.xmlParser())
            val items = doc.select("item").ifEmpty { doc.select("entry") }

            items.mapNotNull { item ->
                try {
                    val title = item.select("title").text().trim()
                    val link = item.select("link").attr("href").ifBlank {
                        item.select("link").text().trim()
                    }
                    val description = extractDescription(item)
                    val content = extractContent(item)
                    val imageUrl = extractImageUrl(item, description, content)
                    val pubDate = extractDate(item)

                    if (title.isBlank() || link.isBlank()) return@mapNotNull null

                    val id = generateId(link)

                    // Truncate content to 50% for readability as requested
                    val truncatedContent = truncateToHalf(content)

                    ArticleEntity(
                        id = id,
                        title = title,
                        description = cleanHtml(description).take(500),
                        content = truncatedContent,
                        imageUrl = imageUrl,
                        sourceUrl = link,
                        sourceName = source.displayName,
                        source = source.name,
                        category = source.category.name,
                        publishedAt = pubDate
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun truncateToHalf(html: String): String {
        if (html.isBlank()) return ""
        val doc = Jsoup.parse(html)
        val text = doc.text()
        if (text.length < 100) return html // Don't truncate very short content

        val halfLength = text.length / 2
        // Find a safe spot to cut (end of a sentence or space)
        var cutPoint = halfLength
        while (cutPoint < text.length && text[cutPoint] != '.' && text[cutPoint] != ' ' && text[cutPoint] != '\n') {
            cutPoint++
        }

        val truncatedText = text.take(cutPoint) + "..."
        return truncatedText
    }

    suspend fun scrapeEvolvingAI(): List<ArticleEntity> = withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect("https://evolvingai.io")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(15000)
                .get()

            val articles = doc.select("article, .post, .entry, .card, [class*=post], [class*=article]")

            articles.mapNotNull { article ->
                try {
                    val titleEl = article.select("h1 a, h2 a, h3 a, .entry-title a, .post-title a").first()
                        ?: article.select("h1, h2, h3, .entry-title, .post-title").first()
                    val title = titleEl?.text()?.trim() ?: return@mapNotNull null
                    val link = titleEl?.attr("abs:href")?.ifBlank {
                        article.select("a").first()?.attr("abs:href")
                    } ?: return@mapNotNull null

                    if (link.isBlank()) return@mapNotNull null

                    val description = article.select(".entry-summary, .post-excerpt, .excerpt, p").first()?.text()?.trim() ?: ""
                    val imageUrl = article.select("img").first()?.let { img ->
                        img.attr("abs:src").ifBlank { img.attr("data-src") }
                    }

                    val id = generateId(link)

                    ArticleEntity(
                        id = id,
                        title = title,
                        description = description.take(500),
                        content = "",
                        imageUrl = imageUrl,
                        sourceUrl = link,
                        sourceName = NewsSource.EVOLVING_AI.displayName,
                        source = NewsSource.EVOLVING_AI.name,
                        category = NewsCategory.AI.name,
                        publishedAt = System.currentTimeMillis()
                    )
                } catch (e: Exception) {
                    null
                }
            }.distinctBy { it.sourceUrl }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun scrapeArticleContent(url: String): String = withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(15000)
                .get()

            val contentSelectors = listOf(
                ".entry-content", ".post-content", ".article-content",
                ".content-body", ".story-body", "article .content",
                ".td-post-content", ".article-body", "#article-body",
                "article", ".post"
            )

            val content = contentSelectors.firstNotNullOfOrNull { selector ->
                doc.select(selector).first()?.html()?.takeIf { it.length > 100 }
            } ?: doc.body()?.html() ?: ""

            // Truncate full scraped content to 50% as well
            truncateToHalf(content)
        } catch (e: Exception) {
            ""
        }
    }

    private fun extractDescription(item: org.jsoup.nodes.Element): String {
        return item.select("description").text().ifBlank {
            item.select("summary").text().ifBlank {
                item.select("content\\:encoded").text().take(500)
            }
        }
    }

    private fun extractContent(item: org.jsoup.nodes.Element): String {
        return item.select("content\\:encoded").html().ifBlank {
            item.select("content").html().ifBlank {
                item.select("description").html()
            }
        }
    }

    private fun extractImageUrl(item: org.jsoup.nodes.Element, description: String, content: String): String? {
        // Check media:content, media:thumbnail, enclosure
        val mediaUrl = item.select("media\\:content, media\\:thumbnail").attr("url").ifBlank {
            item.select("enclosure[type^=image]").attr("url")
        }
        if (mediaUrl.isNotBlank()) return mediaUrl

        // Extract from description HTML or content
        val htmlContent = description.ifBlank { content }
        val imgMatch = Regex("<img[^>]+src=[\"']([^\"']+)[\"']").find(htmlContent)
        return imgMatch?.groupValues?.get(1)
    }

    private fun extractDate(item: org.jsoup.nodes.Element): Long {
        val dateStr = item.select("pubDate").text().ifBlank {
            item.select("published").text().ifBlank {
                item.select("updated").text().ifBlank {
                    item.select("dc\\:date").text()
                }
            }
        }
        if (dateStr.isBlank()) return System.currentTimeMillis()

        for (format in dateFormats) {
            try {
                return format.parse(dateStr)?.time ?: continue
            } catch (_: Exception) {
                continue
            }
        }
        return System.currentTimeMillis()
    }

    private fun cleanHtml(html: String): String {
        return Jsoup.parse(html).text()
    }

    private fun generateId(url: String): String {
        val digest = MessageDigest.getInstance("MD5")
        val bytes = digest.digest(url.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
