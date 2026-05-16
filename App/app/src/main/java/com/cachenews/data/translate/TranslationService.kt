package com.cachenews.data.translate

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.cachenews.data.local.TranslationCacheDao
import com.cachenews.data.local.TranslationCacheEntity
import com.cachenews.data.remote.TranslateApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationService @Inject constructor(
    private val translateApi: TranslateApi,
    private val cacheDao: TranslationCacheDao,
    @ApplicationContext private val context: Context
) {
    private val mutex = Mutex()
    private val pendingTranslations = mutableSetOf<String>()

    companion object {
        private const val MAX_CHUNK_SIZE = 450
        private const val MAX_RETRIES = 2
        private const val RETRY_DELAY_MS = 1000L
    }

    /**
     * Translate text to the target language.
     * Returns null if translation fails and no cache exists.
     */
    suspend fun translate(text: String, sourceLang: String, targetLang: String): String? {
        if (text.isBlank()) return text
        if (sourceLang == targetLang) return text

        val hash = generateHash(text, targetLang)

        // Check cache first
        val cached = cacheDao.getTranslation(hash, targetLang)
        if (cached != null) return cached.translatedText

        // Check if already being translated (dedup)
        val isAlreadyPending = mutex.withLock {
            if (pendingTranslations.contains(hash)) true
            else {
                pendingTranslations.add(hash)
                false
            }
        }
        if (isAlreadyPending) return null

        try {
            // Check network
            if (!isNetworkAvailable()) return null

            // Split into chunks if needed
            val chunks = splitIntoChunks(text)
            val translatedChunks = mutableListOf<String>()

            for (chunk in chunks) {
                val translated = translateWithRetry(chunk, sourceLang, targetLang)
                if (translated != null) {
                    translatedChunks.add(translated)
                } else {
                    // If any chunk fails, return null
                    return null
                }
            }

            val fullTranslation = translatedChunks.joinToString(" ")

            // Cache the result
            cacheDao.insertTranslation(
                TranslationCacheEntity(
                    textHash = hash,
                    originalText = text,
                    translatedText = fullTranslation,
                    targetLanguage = targetLang
                )
            )

            return fullTranslation
        } finally {
            mutex.withLock {
                pendingTranslations.remove(hash)
            }
        }
    }

    private suspend fun translateWithRetry(
        text: String,
        sourceLang: String,
        targetLang: String
    ): String? = withContext(Dispatchers.IO) {
        var lastException: Exception? = null

        repeat(MAX_RETRIES + 1) { attempt ->
            try {
                val langPair = "$sourceLang|$targetLang"
                val response = translateApi.translate(text, langPair)

                if (response.responseStatus == 200 && response.responseData?.translatedText != null) {
                    return@withContext response.responseData.translatedText
                }
            } catch (e: Exception) {
                lastException = e
                if (attempt < MAX_RETRIES) {
                    delay(RETRY_DELAY_MS * (attempt + 1))
                }
            }
        }
        null
    }

    private fun splitIntoChunks(text: String): List<String> {
        if (text.length <= MAX_CHUNK_SIZE) return listOf(text)

        val chunks = mutableListOf<String>()
        val sentences = text.split(Regex("(?<=[.!?])\\s+"))
        val currentChunk = StringBuilder()

        for (sentence in sentences) {
            if (currentChunk.length + sentence.length + 1 > MAX_CHUNK_SIZE) {
                if (currentChunk.isNotEmpty()) {
                    chunks.add(currentChunk.toString().trim())
                    currentChunk.clear()
                }
                // If single sentence is too long, force split
                if (sentence.length > MAX_CHUNK_SIZE) {
                    sentence.chunked(MAX_CHUNK_SIZE).forEach { chunks.add(it) }
                    continue
                }
            }
            if (currentChunk.isNotEmpty()) currentChunk.append(" ")
            currentChunk.append(sentence)
        }
        if (currentChunk.isNotEmpty()) {
            chunks.add(currentChunk.toString().trim())
        }

        return chunks
    }

    private fun generateHash(text: String, targetLang: String): String {
        val input = "$text|$targetLang"
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
