package com.kardas.waterintakereminder.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kardas.waterintakereminder.domain.model.UserProfile
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    private val _onboardingState = MutableStateFlow(UserProfile())
    val onboardingState = _onboardingState.asStateFlow()

    fun updateName(name: String) {
        _onboardingState.update { it.copy(name = name) }
    }

    fun updateWeight(weight: String) {
        val weightFloat = weight.toFloatOrNull() ?: 0f
        _onboardingState.update { it.copy(weightKg = weightFloat) }
    }

    fun updateGender(gender: String) {
        _onboardingState.update { it.copy(gender = gender) }
    }

    fun updateWorkStyle(style: String) {
        _onboardingState.update { it.copy(workStyle = style) }
    }

    fun updateWakeUpTime(time: String) {
        _onboardingState.update { it.copy(wakeUpTime = time) }
    }

    fun updateBedtime(time: String) {
        _onboardingState.update { it.copy(bedtime = time) }
    }

    fun finishOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _onboardingState.value
            
            // Calculate goal based on weight and work style
            val goalMl = repository.calculateGoalMl(state.weightKg, state.workStyle)
            
            val finalProfile = state.copy(
                dailyGoalMl = goalMl,
                isOnboardingComplete = true
            )
            
            repository.saveUserProfile(finalProfile)
            onComplete()
        }
    }
}
