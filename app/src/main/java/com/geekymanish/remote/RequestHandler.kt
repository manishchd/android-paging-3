package com.geekymanish.remote

import com.geekymanish.dto.models.BaseResponse
import retrofit2.Response

fun interface RequestHandler<T> {
    suspend fun sendRequest(): Response<BaseResponse<T>>
}

fun interface PaginationHandler<T> {
    suspend fun sendRequest(page: Int): Response<BaseResponse<T>>
}