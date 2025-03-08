package com.proyek4.datajabar.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyek4.datajabar.ui.components.EduStatCard
import com.proyek4.datajabar.ui.components.FilterBar
import com.proyek4.datajabar.ui.components.SearchBar
import com.proyek4.datajabar.utils.Constant

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var selectedYear by remember { mutableStateOf<String?>(null) }

    val eduStat by homeViewModel.eduStat.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    LaunchedEffect(searchQuery, selectedCity, selectedYear) {
        homeViewModel.fetchEduStat(
            searchQuery.ifEmpty { null },
            selectedCity,
            selectedYear
        )
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ){
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth()
        )

        FilterBar(
            selectedCity = selectedCity,
            onCityChange = { selectedCity = it },
            selectedYear = selectedYear,
            onYearChange = { selectedYear = it },
            cityOptions = Constant().cityOptions,
            yearOptions = Constant().yearOptions,
            onRefresh = {
                selectedCity = null
                selectedYear = null
                searchQuery = ""
            }
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(eduStat) { item ->
                    EduStatCard(
                        namaKabupatenKota = item.namaKabupatenKota,
                        lamaSekolah = item.lamaSekolah,
                        tahun = item.tahun,
                        onClick = {}
                    )
                }
            }
        }
    }
}
