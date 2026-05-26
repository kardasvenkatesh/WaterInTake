package com.kardas.waterintakereminder.domain.model

/**
 * Represents the user's profile and settings.
 */
data class UserProfile(
    val name: String = "",
    val weightKg: Float = 0f,
    val gender: String = "",
    val workStyle: String = "",
    val wakeUpTime: String = "07:00",
    val bedtime: String = "22:30",
    val dailyGoalMl: Int = 2500,
    val isOnboardingComplete: Boolean = false
)
