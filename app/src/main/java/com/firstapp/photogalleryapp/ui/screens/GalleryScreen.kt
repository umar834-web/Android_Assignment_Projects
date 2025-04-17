package com.firstapp.photogalleryapp.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@Composable
fun GalleryScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedFolder by remember { mutableStateOf<File?>(null) }
    var images by remember { mutableStateOf<List<File>>(emptyList()) }
    
    val storagePermission = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    
    val folderPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        uri?.let {
            val folder = File(it.path!!)
            selectedFolder = folder
            loadImagesFromFolder(folder)
        }
    }
    
    LaunchedEffect(Unit) {
        if (!storagePermission.hasPermission) {
            storagePermission.launchPermissionRequest()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Photo Gallery") },
                actions = {
                    IconButton(onClick = { navController.navigate("camera") }) {
                        Icon(Icons.Default.Add, contentDescription = "Take Photo")
                    }
                    IconButton(onClick = { folderPickerLauncher.launch(null) }) {
                        Icon(Icons.Default.Folder, contentDescription = "Select Folder")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (selectedFolder == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Select a folder to view images")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(images) { image ->
                        ImageItem(
                            image = image,
                            onClick = { navController.navigate("image_details/${image.absolutePath}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(image: File, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        AsyncImage(
            model = Uri.fromFile(image),
            contentDescription = image.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun loadImagesFromFolder(folder: File): List<File> {
    return folder.listFiles { file ->
        file.isFile && (file.extension.equals("jpg", ignoreCase = true) ||
                file.extension.equals("jpeg", ignoreCase = true) ||
                file.extension.equals("png", ignoreCase = true))
    }?.toList() ?: emptyList()
} 