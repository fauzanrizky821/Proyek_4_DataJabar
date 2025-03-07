package com.proyek4.datajabar.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
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
import com.proyek4.datajabar.ui.theme.Typography

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    val eduStat by homeViewModel.eduStat.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()


    LaunchedEffect(searchQuery) {
        homeViewModel.fetchEduStat(searchQuery.ifEmpty { null })
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ){
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Cari Kabupaten/Kota") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(eduStat) { item ->
                    Card (
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        elevation = 4.dp
                    ) {
                        Column (
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Nama Kabupaten/Kota: ${item.namaKabupatenKota}",
                                style = Typography.bodyMedium
                            )
                            Text(
                                text = "Lama Sekolah: ${item.lamaSekolah}",
                                style = Typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
