package com.cachenews.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cachenews.data.repository.SettingsRepository
import com.cachenews.notification.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationHelper: NotificationHelper,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val geminiApiKey: StateFlow<String?> = settingsRepository.geminiApiKey
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveApiKey(key: String) {
        viewModelScope.launch {
            settingsRepository.saveGeminiApiKey(key)
        }
    }

    fun deleteApiKey() {
        viewModelScope.launch {
            settingsRepository.deleteGeminiApiKey()
        }
    }

    fun sendTestNotification() {
        notificationHelper.sendTestNotification()
    }
}
