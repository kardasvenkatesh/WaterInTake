package com.kardas.waterintakereminder.domain.model

/**
 * Represents a single instance of water consumed by the user.
 */
data class WaterLog(
    val id: Long = 0,
    val amountMl: Int,
    val timestamp: Long
)
