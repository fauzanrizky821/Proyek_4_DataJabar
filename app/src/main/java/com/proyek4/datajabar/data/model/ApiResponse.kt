package com.proyek4.datajabar.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: Int,
    @SerializedName("data") val data: List<EduStatEntity>
)

data class ApiResponseId(
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: Int,
    @SerializedName("data") val data: EduStatEntity
)