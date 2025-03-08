package com.proyek4.datajabar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterBar(
    selectedCity: String?,
    onCityChange: (String) -> Unit,
    selectedYear: String?,
    onYearChange: (String) -> Unit,
    cityOptions: List<String>,
    yearOptions: List<String>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var expandedCity by remember { mutableStateOf(false) }
        Box(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            OutlinedButton(
                onClick = { expandedCity = true },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape
            ) {
                Text(text = selectedCity ?: "Kota/Kabupaten")
            }
            DropdownMenu(
                expanded = expandedCity,
                onDismissRequest = { expandedCity = false }
            ) {
                cityOptions.forEach { city ->
                    DropdownMenuItem(onClick = {
                        onCityChange(city)
                        expandedCity = false
                    }) {
                        Text(text = city)
                    }
                }
            }
        }

        var expandedYear by remember { mutableStateOf(false) }
        Box(modifier = Modifier.weight(1f)) {
            OutlinedButton(
                onClick = { expandedYear = true },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape
            ) {
                Text(text = selectedYear ?: "Pilih Tahun")
            }
            DropdownMenu(
                expanded = expandedYear,
                onDismissRequest = { expandedYear = false }
            ) {
                yearOptions.forEach { year ->
                    DropdownMenuItem(onClick = {
                        onYearChange(year)
                        expandedYear = false
                    }) {
                        Text(text = year)
                    }
                }
            }
        }

        IconButton(
            onClick = { onRefresh() },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refresh",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}
