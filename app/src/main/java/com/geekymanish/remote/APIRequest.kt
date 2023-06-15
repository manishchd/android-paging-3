package com.geekymanish.remote


import com.geekymanish.dto.models.BaseResponse
import com.geekymanish.dto.models.NotificationModel
import com.geekymanish.remote.NetworkUrls.PASSENGERS
import retrofit2.Response
import retrofit2.http.*

interface APIRequest {

    @GET(PASSENGERS)
    suspend fun notificationHistory(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): Response<BaseResponse<List<NotificationModel>>>

}
