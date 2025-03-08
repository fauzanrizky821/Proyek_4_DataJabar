package com.proyek4.datajabar.data.repository

import android.util.Log
import com.google.gson.Gson
import com.proyek4.datajabar.data.api.ApiService
import com.proyek4.datajabar.data.model.ApiResponse
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class EduStatRepository(private val apiService: ApiService) {

    suspend fun getEduStat(
        searchQuery: String? = null,
        city: String? = null,
        year: String? = null
    ): ApiResponse? {
        return try {
            val whereMap = mutableMapOf<String, Any>()
            city?.let { whereMap["nama_kabupaten_kota"] = it }
            year?.let { whereMap["tahun"] = it.toInt() }

            val whereJson = if (whereMap.isNotEmpty()) {
                Gson().toJson(whereMap)
            } else {
                null
            }

            val response = apiService.getEduStat(
                search = searchQuery,
                where = whereJson
            )

            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("EduStatRepository", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("EduStatRepository", "Exception: ${e.message}")
            null
        }
    }
}
