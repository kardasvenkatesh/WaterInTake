package com.kardas.waterintakereminder.domain.repository

import com.kardas.waterintakereminder.domain.model.PlaceholderModel
import kotlinx.coroutines.flow.Flow

/**
 * Placeholder Repository Interface.
 */
interface PlaceholderRepository {
    fun getPlaceholders(): Flow<List<PlaceholderModel>>
    suspend fun addPlaceholder(name: String)
}

