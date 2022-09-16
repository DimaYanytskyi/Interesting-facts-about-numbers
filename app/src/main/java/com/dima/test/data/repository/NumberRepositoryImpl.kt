package com.dima.test.data.repository

import com.dima.test.data.data_source.local.NumbersDao
import com.dima.test.data.data_source.local.entities.NumberInfoEntity
import com.dima.test.data.data_source.remote.NumbersApi
import com.dima.test.data.data_source.remote.dto.NumberDto
import com.dima.test.domain.repository.NumberRepository
import javax.inject.Inject

class NumberRepositoryImpl @Inject constructor(
    private val api: NumbersApi,
    private val db: NumbersDao
) : NumberRepository{

    override suspend fun fetchInfoByNumber(number: Int): NumberDto {
        return api.fetchInfoByNumber(number)
    }

    override suspend fun fetchInfoByRandomNumber(): NumberDto {
        return api.fetchInfoByRandomNumber()
    }

    override suspend fun getRecent(): List<NumberInfoEntity> {
        return db.getRecent()
    }

    override fun insertRecent(listOfRecent: List<NumberInfoEntity>) {
        return db.insertRecent(listOfRecent)
    }
}