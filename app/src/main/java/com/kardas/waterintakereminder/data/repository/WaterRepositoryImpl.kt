package com.kardas.waterintakereminder.data.repository

import com.kardas.waterintakereminder.data.datastore.UserPreferences
import com.kardas.waterintakereminder.data.local.dao.WaterLogDao
import com.kardas.waterintakereminder.data.local.entity.WaterLogEntity
import com.kardas.waterintakereminder.domain.model.UserProfile
import com.kardas.waterintakereminder.domain.model.WaterLog
import com.kardas.waterintakereminder.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterRepositoryImpl @Inject constructor(
    private val waterLogDao: WaterLogDao,
    private val userPreferences: UserPreferences
) : WaterRepository {

    override fun getUserProfile(): Flow<UserProfile> {
        return userPreferences.userProfileFlow
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        userPreferences.saveUserProfile(profile)
    }

    override suspend fun updateOnboardingStatus(isComplete: Boolean) {
        userPreferences.updateOnboardingStatus(isComplete)
    }

    override fun getWaterLogsForToday(): Flow<List<WaterLog>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return waterLogDao.getWaterLogsForDay(startOfDay, endOfDay).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getWeeklyWaterLogs(): Flow<List<WaterLog>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfWeek = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfWeek = calendar.timeInMillis

        return waterLogDao.getWaterLogsForWeek(startOfWeek, endOfWeek).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addWaterLog(amountMl: Int) {
        val entity = WaterLogEntity(
            amountMl = amountMl,
            timestamp = System.currentTimeMillis()
        )
        waterLogDao.insertWaterLog(entity)
    }

    override suspend fun deleteWaterLog(id: Long) {
        waterLogDao.deleteWaterLog(id)
    }

    override fun getConsumedWaterToday(): Flow<Int> {
        return getWaterLogsForToday().map { logs ->
            logs.sumOf { it.amountMl }
        }
    }

    override suspend fun calculateGoalMl(weightKg: Float, workStyle: String): Int {
        var baseGoal = (weightKg * 35).toInt()
        
        // Adjust based on work style
        when (workStyle.lowercase()) {
            "active", "workout" -> baseGoal += 500
            "student", "desk job" -> baseGoal += 100 // Slight increase for focus
        }
        
        return baseGoal
    }
}
