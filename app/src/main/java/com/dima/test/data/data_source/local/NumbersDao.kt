package com.dima.test.data.data_source.local

import androidx.room.*
import com.dima.test.data.data_source.local.entities.NumberInfoEntity

@Dao
interface NumbersDao {

    @Query("SELECT * FROM numbers")
    suspend fun getRecent() : List<NumberInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecent(listOfRecent: List<NumberInfoEntity>)
}