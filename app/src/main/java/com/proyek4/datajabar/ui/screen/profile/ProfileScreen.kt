package com.proyek4.datajabar.ui.screen.profile

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.yalantis.ucrop.UCrop
import com.proyek4.datajabar.data.model.ProfileEntity


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(LocalContext.current))) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }
    var studentName by remember { mutableStateOf("Mahasiswa") }
    var studentId by remember { mutableStateOf("231511076") }
    var studentEmail by remember { mutableStateOf("mahasiswa@polban.ac.id") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val profileData by profileViewModel.dataProfile.observeAsState()

    val cropImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let { uri ->
                    profileViewModel.updateProfileImage(uri, context)
                    imageUri = uri
                }
            }
        }
    )

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                profileViewModel.startCrop(it, context, cropImageLauncher)
            }
        }
    )

    LaunchedEffect(profileData, imageUri) {
        studentId = profileData?.id.toString()
        studentName = profileData?.name ?: ""
        studentEmail = profileData?.email ?: ""
        imageUri = profileData?.imgUrl?.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
        Log.d("ProfileScreen", "Image URI: $imageUri")
        Log.d("ProfileScreen", "Current Image URI: $imageUri")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                imageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .background(Color.LightGray, CircleShape),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("Student ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentEmail,
                    onValueChange = { studentEmail = it },
                    label = { Text("Student Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    {
                        pickImageLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Upload Photo")
                }
//                ImagePicker()
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Student Name: $studentName")
                        Log.d("ProfileScreen", "Student ID: $studentId")
                        Log.d("ProfileScreen", "Student Email: $studentEmail")
                        Log.d("ProfileScreen", "Image URI: $imageUri")

                        val updatedProfile = ProfileEntity(
                            id = profileData?.id ?: 0,
                            name = studentName,
                            email = studentEmail,
                            imgUrl = imageUri.toString()
                        )
                        profileViewModel.updateProfile(updatedProfile)

                        isEditing = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            } else {
                Text(
                    text = studentName,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = studentId,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = studentEmail,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { isEditing = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit Profile")
                }
            }
        }
    }

}