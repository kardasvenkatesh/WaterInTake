package com.kardas.waterintakereminder.presentation.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import com.kardas.waterintakereminder.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InsightsData(
    val avgDailyMl: Int,
    val bestDayMl: Int
)

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<InsightsData>>(UiState.Loading)
    val uiState: StateFlow<UiState<InsightsData>> = _uiState.asStateFlow()

    init {
        loadInsights()
    }

    private fun loadInsights() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getWeeklyWaterLogs()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown error")
                }
                .collect { logs ->
                    if (logs.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        // Group logs by day and sum
                        val dailyTotals = logs.groupBy { it.timestamp / (1000 * 60 * 60 * 24) }
                            .mapValues { entry -> entry.value.sumOf { it.amountMl } }
                            .values
                        
                        val avg = if (dailyTotals.isNotEmpty()) dailyTotals.average().toInt() else 0
                        val best = if (dailyTotals.isNotEmpty()) dailyTotals.maxOrNull() ?: 0 else 0
                        
                        _uiState.value = UiState.Success(
                            InsightsData(
                                avgDailyMl = avg,
                                bestDayMl = best
                            )
                        )
                    }
                }
        }
    }
}
