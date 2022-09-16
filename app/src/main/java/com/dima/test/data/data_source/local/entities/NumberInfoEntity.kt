package com.dima.test.data.data_source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
data class NumberInfoEntity(
    val number: Int,
    val fact: String,
    val found: Boolean,
    @PrimaryKey val id: Int? = null
)
