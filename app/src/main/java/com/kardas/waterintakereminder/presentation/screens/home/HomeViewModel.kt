package com.kardas.waterintakereminder.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import com.kardas.waterintakereminder.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardData(
    val userName: String,
    val consumedMl: Int,
    val targetMl: Int,
    val streakDays: Int = 1 // Mock for MVP
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DashboardData>>(UiState.Loading)
    val uiState: StateFlow<UiState<DashboardData>> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            combine(
                repository.getUserProfile(),
                repository.getConsumedWaterToday()
            ) { profile, consumed ->
                DashboardData(
                    userName = profile.name,
                    consumedMl = consumed,
                    targetMl = profile.dailyGoalMl
                )
            }
            .catch { e ->
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
            .collect { data ->
                _uiState.value = UiState.Success(data)
            }
        }
    }

    fun quickAddWater(amountMl: Int) {
        viewModelScope.launch {
            repository.addWaterLog(amountMl)
        }
    }
}
