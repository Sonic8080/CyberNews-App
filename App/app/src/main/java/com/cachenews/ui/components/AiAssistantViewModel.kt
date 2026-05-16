package com.cachenews.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cachenews.data.gemini.GeminiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Message(
    val content: String,
    val role: String // "user" or "model"
)

data class AiAssistantUiState(
    val messages: List<Message> = listOf(
        Message("Hello! I'm your CyberNews Assistant. How can I help you with cybersecurity and AI news today?", "model")
    ),
    val isTyping: Boolean = false,
    val articleTitle: String? = null,
    val articleContent: String? = null
)

@HiltViewModel
class AiAssistantViewModel @Inject constructor(
    private val geminiService: GeminiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiAssistantUiState())
    val uiState: StateFlow<AiAssistantUiState> = _uiState.asStateFlow()

    fun setContext(title: String, content: String) {
        _uiState.update { 
            it.copy(
                articleTitle = title, 
                articleContent = content,
                messages = listOf(
                    Message("I've analyzed the article: '$title'. Ask me anything about its security implications or technical details!", "model")
                )
            ) 
        }
    }

    fun clearChat() {
        _uiState.update {
            AiAssistantUiState()
        }
    }

    fun sendMessage(text: String) {
        val userMessage = Message(text, "user")
        _uiState.update { 
            it.copy(
                messages = it.messages + userMessage,
                isTyping = true
            )
        }

        viewModelScope.launch {
            val response = if (_uiState.value.articleContent != null) {
                geminiService.askAboutArticle(
                    _uiState.value.articleTitle ?: "",
                    _uiState.value.articleContent ?: "",
                    text
                )
            } else {
                geminiService.generateResponse(text)
            }

            _uiState.update { 
                it.copy(
                    messages = it.messages + Message(response ?: "Sorry, I couldn't process that request.", "model"),
                    isTyping = false
                )
            }
        }
    }
}
