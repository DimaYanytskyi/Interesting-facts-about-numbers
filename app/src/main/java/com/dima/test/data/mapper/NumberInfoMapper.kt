package com.dima.test.data.mapper

import com.dima.test.data.data_source.local.entities.NumberInfoEntity
import com.dima.test.data.data_source.remote.dto.NumberDto
import com.dima.test.domain.model.NumberInfo

fun NumberInfo.toNumberInfoDB() : NumberInfoEntity {
    return NumberInfoEntity(
        number, fact, found, number
    )
}

fun NumberDto.toNumber() : NumberInfo = NumberInfo(
    number,
    number,
    text,
    found
)

fun NumberInfoEntity.toNumber() : NumberInfo = NumberInfo(
    number,
    number,
    fact,
    found
)