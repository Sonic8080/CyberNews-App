package com.cachenews.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cachenews.data.repository.SettingsRepository
import com.cachenews.domain.model.AppLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StartUiState(
    val isFirstLaunch: Boolean = false,
    val isLoading: Boolean = false,
    val selectedLanguage: AppLanguage = AppLanguage.ENGLISH,
    val showLanguageDialog: Boolean = false
)

@HiltViewModel
class StartViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.isFirstLaunch,
                settingsRepository.selectedLanguage
            ) { isFirst, lang ->
                // Always set showLanguageDialog to false to remove initial selection
                StartUiState(
                    isFirstLaunch = isFirst,
                    isLoading = false,
                    selectedLanguage = lang,
                    showLanguageDialog = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun selectLanguage(language: AppLanguage) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
            _uiState.update {
                it.copy(
                    selectedLanguage = language,
                    showLanguageDialog = false,
                    isFirstLaunch = false
                )
            }
        }
    }

    fun dismissLanguageDialog() {
        _uiState.update { it.copy(showLanguageDialog = false) }
    }
}
