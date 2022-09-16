package com.dima.test.data.data_source.remote.dto

import com.dima.test.domain.model.NumberInfo

data class NumberDto(
    val found: Boolean,
    val number: Int,
    val text: String,
    val type: String
)