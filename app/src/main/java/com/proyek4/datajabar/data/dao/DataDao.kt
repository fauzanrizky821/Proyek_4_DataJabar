package com.proyek4.datajabar.data.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.proyek4.datajabar.data.model.DataEntity
import com.proyek4.datajabar.data.model.ProfileEntity


@Dao
interface DataDao {

//    @Insert
//    suspend fun insert(data: DataEntity)
//
//    @Update
//    suspend fun update(data: DataEntity)

    @Query("SELECT * FROM data_table ORDER BY id DESC")
    fun getAll(): LiveData<List<DataEntity>>

    @Query("SELECT * FROM data_table WHERE id = :dataId")
    suspend fun getById(dataId: Int): DataEntity?

    @Delete
    suspend fun delete(data: DataEntity)

    @Insert
    suspend fun insert(data: ProfileEntity)

    @Update
    suspend fun update(data: ProfileEntity)

    @Query("SELECT * FROM profile_table ORDER BY id DESC")
    fun getAllProfile(): LiveData<List<ProfileEntity?>>

    @Query("SELECT * FROM profile_table WHERE id = :id")
    fun getProfileById(id: Int): LiveData<ProfileEntity?>

    @Query("SELECT * FROM profile_table ORDER BY id ASC LIMIT 1")
    fun getClosestProfile(): LiveData<ProfileEntity?>

    @Query("UPDATE profile_table SET image_url = :imgUrl WHERE id = :id")
    suspend fun updateProfileImage(id: Int, imgUrl: String) {
        Log.d("ProfileViewModel", "Image URL: $imgUrl")
    }
}
