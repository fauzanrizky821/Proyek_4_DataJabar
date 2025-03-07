package com.proyek4.datajabar.ui.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _message = MutableStateFlow("Welcome to Home Screen")
    val message = _message.asStateFlow()
}