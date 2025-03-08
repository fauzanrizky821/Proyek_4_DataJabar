package com.proyek4.datajabar.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyek4.datajabar.ui.theme.Typography

@Composable
fun DetailScreen(DetailViewModel: DetailViewModel = viewModel(), id: Int) {
    val eduStat by DetailViewModel.eduStat.collectAsState()
    val isLoading by DetailViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        DetailViewModel.getEduStatById(id)
    }

    val tingkatPendidikan = when {
        eduStat.lamaSekolah <= 6 -> "SD"
        eduStat.lamaSekolah <= 9 -> "SMP"
        else -> "SMA"
    }

    val labelNama = if (eduStat.namaKabupatenKota.startsWith("KABUPATEN")) "Nama Kabupaten" else "Nama Kota"
    val labelKode = if (eduStat.namaKabupatenKota.startsWith("KABUPATEN")) "Kode Kabupaten" else "Kode Kota"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                backgroundColor = Color(0xFFFAFAFA),
                modifier = Modifier.padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Detail ${eduStat.namaKabupatenKota}",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed molestie sem eu diam efficitur venenatis. Nulla in nunc eget magna mollis bibendum. Nunc placerat sapien ac est tempus bibendum. Donec non nisi libero. Aliquam et dapibus orci. Sed orci lacus, pulvinar sit amet eleifend id, consequat sit amet lorem. Nulla facilisi. Nam vulputate elementum neque, non dapibus erat interdum ac. Nulla facilisi. Pellentesque nec ipsum faucibus, tincidunt mauris nec, feugiat libero. Ut vitae fermentum nunc.",
                        style = Typography.labelMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InfoRow(label = labelNama, value = eduStat.namaKabupatenKota)
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = labelKode, value = eduStat.kodeKabupatenKota.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Tahun", value = eduStat.tahun.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Rata-rata Lama Sekolah", value = "${eduStat.lamaSekolah} Tahun")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Tingkat Pendidikan", value = tingkatPendidikan)
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = buildAnnotatedString {
                append("$label: ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(value)
                }
            },
            style = Typography.labelMedium,
            fontSize = 13.sp
        )
    }
}
