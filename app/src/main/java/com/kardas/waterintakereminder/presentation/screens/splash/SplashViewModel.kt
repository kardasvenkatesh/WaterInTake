package com.kardas.waterintakereminder.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<SplashNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            // Add a small delay for the splash screen animation
            delay(1500)
            
            val profile = repository.getUserProfile().first()
            if (profile.isOnboardingComplete) {
                _navigationEvent.emit(SplashNavigationEvent.NavigateToHome)
            } else {
                _navigationEvent.emit(SplashNavigationEvent.NavigateToOnboarding)
            }
        }
    }
}

sealed class SplashNavigationEvent {
    object NavigateToHome : SplashNavigationEvent()
    object NavigateToOnboarding : SplashNavigationEvent()
}
