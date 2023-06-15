package com.geekymanish.dto.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirlineModel(
    @SerializedName("logo") val logo: String? = null,
)
