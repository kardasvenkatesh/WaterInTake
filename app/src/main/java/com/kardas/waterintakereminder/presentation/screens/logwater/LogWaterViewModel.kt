package com.kardas.waterintakereminder.presentation.screens.logwater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogWaterViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    private val _customAmount = MutableStateFlow("")
    val customAmount = _customAmount.asStateFlow()

    fun updateAmount(amount: String) {
        if (amount.isEmpty() || amount.all { it.isDigit() }) {
            _customAmount.update { amount }
        }
    }

    fun saveWaterLog(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val amount = _customAmount.value.toIntOrNull()
            if (amount != null && amount > 0) {
                repository.addWaterLog(amount)
                onSuccess()
            }
        }
    }
}
