package com.proyek4.datajabar.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek4.datajabar.data.model.EduStatEntity
import com.proyek4.datajabar.data.repository.EduStatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _eduStat = MutableStateFlow<List<EduStatEntity>>(emptyList())
    val eduStat = _eduStat.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun fetchEduStat(searchQuery: String? = null, city: String? = null, year: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = EduStatRepository(ApiClient.apiService).getEduStat(searchQuery, city, year)
                Log.d("HomeViewModel", "Response: $response")

                if (response != null) {
                    _eduStat.value = response.data
                } else {
                    _errorMessage.value = "Gagal mendapatkan data"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Terjadi kesalahan"
                _eduStat.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}