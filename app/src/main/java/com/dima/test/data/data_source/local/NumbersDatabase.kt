package com.dima.test.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dima.test.data.data_source.local.entities.NumberInfoEntity

@Database(
    entities = [NumberInfoEntity::class],
    version = 1
)
abstract class NumbersDatabase : RoomDatabase() {
    abstract val noteDao : NumbersDao
}