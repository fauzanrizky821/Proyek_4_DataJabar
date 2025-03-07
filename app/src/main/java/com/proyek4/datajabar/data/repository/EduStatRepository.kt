package com.proyek4.datajabar.data.repository

import android.util.Log
import com.proyek4.datajabar.data.api.ApiService
import com.proyek4.datajabar.data.model.ApiResponse

class EduStatRepository(private val apiService: ApiService) {

    suspend fun getEduStat(searchQuery: String? = null): ApiResponse? {
        return try {
            val response = apiService.getEduStat(searchQuery)
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
