package com.firstapp.photogalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firstapp.photogalleryapp.ui.theme.PhotoGalleryAppTheme
import com.firstapp.photogalleryapp.ui.screens.GalleryScreen
import com.firstapp.photogalleryapp.ui.screens.ImageDetailsScreen
import com.firstapp.photogalleryapp.ui.screens.CameraScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoGalleryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = "gallery") {
                        composable("gallery") {
                            GalleryScreen(navController)
                        }
                        composable("camera") {
                            CameraScreen(navController)
                        }
                        composable("image_details/{imagePath}") { backStackEntry ->
                            val imagePath = backStackEntry.arguments?.getString("imagePath")
                            if (imagePath != null) {
                                ImageDetailsScreen(navController, imagePath)
                            }
                        }
                    }
                }
            }
        }
    }
}