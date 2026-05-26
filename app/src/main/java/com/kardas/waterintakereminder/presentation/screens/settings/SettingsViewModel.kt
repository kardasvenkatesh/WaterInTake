package com.kardas.waterintakereminder.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kardas.waterintakereminder.domain.model.UserProfile
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import com.kardas.waterintakereminder.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<UserProfile>>(UiState.Loading)
    val uiState: StateFlow<UiState<UserProfile>> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getUserProfile()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown error")
                }
                .collect { profile ->
                    _uiState.value = UiState.Success(profile)
                }
        }
    }

    fun recalculateGoal() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is UiState.Success) {
                val profile = currentState.data
                val newGoal = repository.calculateGoalMl(profile.weightKg, profile.workStyle)
                repository.saveUserProfile(profile.copy(dailyGoalMl = newGoal))
            }
        }
    }
}
