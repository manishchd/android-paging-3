package com.geekymanish.dto.models

data class BaseResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val totalPages: Int? = null,
    val totalPassengers: Int? = null,
)