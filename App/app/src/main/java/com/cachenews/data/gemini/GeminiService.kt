package com.cachenews.data.gemini

import com.cachenews.data.repository.SettingsRepository
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    private suspend fun getModel(): GenerativeModel? {
        val apiKey = settingsRepository.getGeminiApiKeySync()
        if (apiKey.isNullOrBlank()) return null

        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey
        )
    }

    suspend fun generateResponse(prompt: String): String? {
        val model = getModel() ?: return "Please configure your Gemini API Key in Settings to use the assistant."
        return try {
            val response = model.generateContent(prompt)
            response.text
        } catch (e: Exception) {
            "Error: ${e.localizedMessage ?: "Failed to generate response"}"
        }
    }

    suspend fun askAboutArticle(articleTitle: String, articleContent: String, userQuestion: String): String? {
        val prompt = """
            You are the CyberNews Assistant, a specialized AI designed to help users understand cybersecurity and AI-related news.
            
            Context article title: $articleTitle
            Context article content: $articleContent
            
            User Question: $userQuestion
            
            Please provide a helpful, expert, and concise answer based on the article content provided above. 
            Maintain a professional yet accessible tone suitable for a tech-savvy audience.
        """.trimIndent()

        return generateResponse(prompt)
    }
}
