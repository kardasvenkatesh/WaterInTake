package com.kardas.waterintakereminder.domain.model

/**
 * Represents the daily hydration goal and progress.
 */
data class DailyGoal(
    val targetMl: Int,
    val consumedMl: Int,
    val dateMillis: Long
)
