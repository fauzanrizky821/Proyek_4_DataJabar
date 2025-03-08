package com.proyek4.datajabar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.proyek4.datajabar.data.database.AppDatabase
import com.proyek4.datajabar.data.model.ProfileEntity
import com.proyek4.datajabar.ui.navigation.BottomBar
import com.proyek4.datajabar.ui.navigation.NavigationGraph
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val userDao = db.dataDao()
            val userCount = userDao.getAll().value?.size ?: 0
            Log.d("MainActivity", "User count: $userCount")
            if (userCount == 0) {
                userDao.insert(
                    ProfileEntity(
                        name = "Fauzan",
                        email = "fauzan.tif23@polban.ac.id",
                        imgUrl = "",
                    )
                )
                Log.d("MainActivity", "User inserted")
            }
        }

        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}