package com.kardas.waterintakereminder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kardas.waterintakereminder.data.local.dao.WaterLogDao
import com.kardas.waterintakereminder.data.local.entity.WaterLogEntity

/**
 * Room App Database.
 */
@Database(
    entities = [WaterLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val waterLogDao: WaterLogDao
}

