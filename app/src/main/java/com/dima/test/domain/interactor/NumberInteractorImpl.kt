package com.dima.test.domain.interactor

import com.dima.test.common.Response
import com.dima.test.data.mapper.toNumber
import com.dima.test.data.mapper.toNumberInfoDB
import com.dima.test.domain.model.NumberInfo
import com.dima.test.domain.repository.NumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NumberInteractorImpl @Inject constructor(
    private val numberRepository: NumberRepository
) : NumberInteractor {

    override fun fetchInfoByNumber(number: Int): Flow<Response<NumberInfo>> = flow {
        try {
            emit(Response.Loading())
            val numberInfo = numberRepository.fetchInfoByNumber(number).toNumber()
            emit(Response.Success(numberInfo))
        } catch(e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun fetchInfoByRandomNumber(): Flow<Response<NumberInfo>> = flow {
        try {
            emit(Response.Loading())
            val numberInfo = numberRepository.fetchInfoByRandomNumber().toNumber()
            emit(Response.Success(numberInfo))
        } catch(e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun getRecent(): Flow<Response<List<NumberInfo>>> = flow {
        try {
            emit(Response.Loading())
            val recent = numberRepository.getRecent().map { it.toNumber() }
            emit(Response.Success(recent))
        } catch(e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun insertRecent(listOfRecent: List<NumberInfo>) {
        numberRepository.insertRecent(listOfRecent.map { it.toNumberInfoDB() })
    }
}