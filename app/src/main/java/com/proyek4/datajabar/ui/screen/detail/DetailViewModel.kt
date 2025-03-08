package com.proyek4.datajabar.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyek4.datajabar.data.model.EduStatEntity
import com.proyek4.datajabar.data.repository.EduStatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    private val _eduStat = MutableStateFlow<EduStatEntity>(
        EduStatEntity(
            0,
            0,
            "",
            0,
            "",
            0.0,
            "",
            0
        ))
    val eduStat = _eduStat.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getEduStatById(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val eduStat = EduStatRepository(ApiClient.apiService).getEduStatById(id)
                Log.d("DetailViewModel", "Response: $eduStat")

                if (eduStat != null) {
                    _eduStat.value = eduStat
                } else {
                    _errorMessage.value = "Gagal mendapatkan data"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }

}