package com.dima.test.data.data_source.remote

import com.dima.test.data.data_source.remote.dto.NumberDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {

    @GET("{number}?json")
    suspend fun fetchInfoByNumber(
        @Path("number") number: Int
    ) : NumberDto

    @GET("random/math?json")
    suspend fun fetchInfoByRandomNumber() : NumberDto
}