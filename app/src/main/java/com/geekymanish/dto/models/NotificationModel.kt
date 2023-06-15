package com.geekymanish.dto.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("logo") val logo: String? = null,
    @SerializedName("airline") val airline: List<AirlineModel>? = null,
)