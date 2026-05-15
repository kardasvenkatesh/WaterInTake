package com.template.app.data.repository

import com.template.app.data.local.dao.PlaceholderDao
import com.template.app.data.local.entity.PlaceholderEntity
import com.template.app.domain.model.PlaceholderModel
import com.template.app.domain.repository.PlaceholderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of PlaceholderRepository.
 */
class PlaceholderRepositoryImpl @Inject constructor(
    private val dao: PlaceholderDao
) : PlaceholderRepository {

    override fun getPlaceholders(): Flow<List<PlaceholderModel>> {
        return dao.getAll().map { list ->
            list.map { PlaceholderModel(it.id, it.name) }
        }
    }

    override suspend fun addPlaceholder(name: String) {
        dao.insert(PlaceholderEntity(name = name))
    }
}
