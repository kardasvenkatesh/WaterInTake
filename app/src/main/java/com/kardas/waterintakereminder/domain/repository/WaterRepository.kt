package com.kardas.waterintakereminder.domain.repository

import com.kardas.waterintakereminder.domain.model.UserProfile
import com.kardas.waterintakereminder.domain.model.WaterLog
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the data operations for the app.
 */
interface WaterRepository {
    // User Profile
    fun getUserProfile(): Flow<UserProfile>
    suspend fun saveUserProfile(profile: UserProfile)
    suspend fun updateOnboardingStatus(isComplete: Boolean)

    // Water Logs
    fun getWaterLogsForToday(): Flow<List<WaterLog>>
    fun getWeeklyWaterLogs(): Flow<List<WaterLog>>
    suspend fun addWaterLog(amountMl: Int)
    suspend fun deleteWaterLog(id: Long)

    // Daily Goals (calculated based on logs and profile)
    fun getConsumedWaterToday(): Flow<Int>
    suspend fun calculateGoalMl(weightKg: Float, workStyle: String): Int
}
