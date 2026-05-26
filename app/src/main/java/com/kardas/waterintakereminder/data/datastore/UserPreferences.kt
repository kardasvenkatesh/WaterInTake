package com.kardas.waterintakereminder.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kardas.waterintakereminder.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val NAME = stringPreferencesKey("name")
        val WEIGHT_KG = floatPreferencesKey("weight_kg")
        val GENDER = stringPreferencesKey("gender")
        val WORK_STYLE = stringPreferencesKey("work_style")
        val WAKE_UP_TIME = stringPreferencesKey("wake_up_time")
        val BEDTIME = stringPreferencesKey("bedtime")
        val DAILY_GOAL_ML = intPreferencesKey("daily_goal_ml")
        val IS_ONBOARDING_COMPLETE = booleanPreferencesKey("is_onboarding_complete")
    }

    val userProfileFlow: Flow<UserProfile> = dataStore.data.map { preferences ->
        UserProfile(
            name = preferences[Keys.NAME] ?: "",
            weightKg = preferences[Keys.WEIGHT_KG] ?: 0f,
            gender = preferences[Keys.GENDER] ?: "",
            workStyle = preferences[Keys.WORK_STYLE] ?: "",
            wakeUpTime = preferences[Keys.WAKE_UP_TIME] ?: "07:00",
            bedtime = preferences[Keys.BEDTIME] ?: "22:30",
            dailyGoalMl = preferences[Keys.DAILY_GOAL_ML] ?: 2500,
            isOnboardingComplete = preferences[Keys.IS_ONBOARDING_COMPLETE] ?: false
        )
    }

    suspend fun saveUserProfile(profile: UserProfile) {
        dataStore.edit { preferences ->
            preferences[Keys.NAME] = profile.name
            preferences[Keys.WEIGHT_KG] = profile.weightKg
            preferences[Keys.GENDER] = profile.gender
            preferences[Keys.WORK_STYLE] = profile.workStyle
            preferences[Keys.WAKE_UP_TIME] = profile.wakeUpTime
            preferences[Keys.BEDTIME] = profile.bedtime
            preferences[Keys.DAILY_GOAL_ML] = profile.dailyGoalMl
            preferences[Keys.IS_ONBOARDING_COMPLETE] = profile.isOnboardingComplete
        }
    }

    suspend fun updateOnboardingStatus(isComplete: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.IS_ONBOARDING_COMPLETE] = isComplete
        }
    }
}
