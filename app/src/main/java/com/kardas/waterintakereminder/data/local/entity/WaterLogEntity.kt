package com.kardas.waterintakereminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kardas.waterintakereminder.domain.model.WaterLog

@Entity(tableName = "water_logs")
data class WaterLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amountMl: Int,
    val timestamp: Long
) {
    fun toDomainModel(): WaterLog {
        return WaterLog(
            id = id,
            amountMl = amountMl,
            timestamp = timestamp
        )
    }
}
