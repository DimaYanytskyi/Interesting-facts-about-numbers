package com.dima.test.domain.interactor

import com.dima.test.common.Response
import com.dima.test.domain.model.NumberInfo
import kotlinx.coroutines.flow.Flow

interface NumberInteractor {
    fun fetchInfoByNumber(number: Int) : Flow<Response<NumberInfo>>
    fun fetchInfoByRandomNumber() : Flow<Response<NumberInfo>>

    fun getRecent() : Flow<Response<List<NumberInfo>>>
    fun insertRecent(listOfRecent: List<NumberInfo>)
}