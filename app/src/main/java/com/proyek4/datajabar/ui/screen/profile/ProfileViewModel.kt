package com.proyek4.datajabar.ui.screen.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyek4.datajabar.data.database.AppDatabase
import com.proyek4.datajabar.data.model.ProfileEntity
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel(context: Context) : ViewModel() {

    private val dao = AppDatabase.getDatabase(context).dataDao()
    val dataProfile = dao.getProfileById(1)

    init {
        viewModelScope.launch {
            val existingProfile = dao.getProfileById(1).value
            if (existingProfile == null) {
                dao.insert(ProfileEntity(name ="Fauzan", email = "fauzan@polban.ac.id", imgUrl = ""))
                Log.d("ProfileViewModel", "Profile default dimasukkan!")
            }
        }
    }

    fun updateProfile(profile: ProfileEntity) {
        viewModelScope.launch {
            val existingProfile = dao.getProfileById(1).value
            if (existingProfile == null) {
                Log.d("ProfileViewModel", "Profile tidak ada!")
                dao.insert(ProfileEntity(name = profile.name, email = profile.email, imgUrl = profile.imgUrl))
            } else {
                Log.d("ProfileViewModel", "Profile updated!")
                dao.update(profile)
                Log.d("ProfileViewModel", "Profile updated!")
            }
        }
    }

    fun updateProfileImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            val imageUrl = saveImageToLocalStorage(context, uri)
            updateProfileImageUri(1, imageUrl)

            val updatedProfile = dao.getProfileById(1).value
            updatedProfile?.let {
                dao.update(it)
            }

            Log.d("ProfileViewModel", "Image URL: $imageUrl")
        }
    }

    fun startCrop(sourceUri: Uri, context: Context, cropImageLauncher: ActivityResultLauncher<Intent>) {
        val destinationUri = Uri.fromFile(File(context.cacheDir, "cropped.jpg"))
        val intent = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(800, 800)
            .getIntent(context)

        cropImageLauncher.launch(intent)
    }

    private suspend fun saveImageToLocalStorage(context: Context, uri: Uri): String {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, "profile_image.jpg")
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file.absolutePath
        }
    }

    private suspend fun updateProfileImageUri(id: Int, imageUrl: String) {
        withContext(Dispatchers.IO) {
            dao.updateProfileImage(id, imageUrl)
        }
    }
}

class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}