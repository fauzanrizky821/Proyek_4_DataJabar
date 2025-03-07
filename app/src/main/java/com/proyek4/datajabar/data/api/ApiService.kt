package com.proyek4.datajabar.data.api

import com.proyek4.datajabar.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("od_17046_rata_rata_lama_sekolah_berdasarkan_kabupatenkota")
    suspend fun getEduStat(
        @Query("search") search: String? = null
    ): Response<ApiResponse>
}