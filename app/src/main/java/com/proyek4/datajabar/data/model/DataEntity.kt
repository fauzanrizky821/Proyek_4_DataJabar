package com.proyek4.datajabar.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kodeProvinsi: String,
    val namaProvinsi: String,
    val kodeKabupatenKota: String,
    val namaKabupatenKota: String,
    val total: Double,
    val satuan: String,
    val tahun: Int
)

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    @ColumnInfo(name = "image_url")
    val imgUrl: String,
)
