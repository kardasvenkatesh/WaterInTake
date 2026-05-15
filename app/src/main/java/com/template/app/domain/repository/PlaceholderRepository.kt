package com.template.app.domain.repository

import com.template.app.domain.model.PlaceholderModel
import kotlinx.coroutines.flow.Flow

/**
 * Placeholder Repository Interface.
 */
interface PlaceholderRepository {
    fun getPlaceholders(): Flow<List<PlaceholderModel>>
    suspend fun addPlaceholder(name: String)
}
