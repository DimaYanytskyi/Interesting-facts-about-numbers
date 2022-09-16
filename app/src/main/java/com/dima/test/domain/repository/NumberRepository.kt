package com.dima.test.domain.repository

import com.dima.test.data.data_source.local.entities.NumberInfoEntity
import com.dima.test.data.data_source.remote.dto.NumberDto

interface NumberRepository {
    suspend fun fetchInfoByNumber(number: Int) : NumberDto
    suspend fun fetchInfoByRandomNumber() : NumberDto

    suspend fun getRecent() : List<NumberInfoEntity>
    fun insertRecent(listOfRecent: List<NumberInfoEntity>)
}