package com.proyek4.datajabar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.proyek4.datajabar.R
import com.proyek4.datajabar.ui.theme.Typography
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EduStatCard(
    namaKabupatenKota: String,
    lamaSekolah: Double,
    tahun: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable(onClick = onClick),
        elevation = 6.dp,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xFFF8F9FA)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = namaKabupatenKota,
                style = Typography.titleMedium,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(iconRes = R.drawable.ic_toga, label = "Lama Sekolah:", value = "$lamaSekolah Tahun")
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow(iconRes = R.drawable.ic_calendar, label = "Tahun:", value = tahun.toString())
        }
    }
}

@Composable
fun InfoRow(iconRes: Int, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                append("$label ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(value)
                }
            },
            style = Typography.bodyMedium
        )
    }
}
