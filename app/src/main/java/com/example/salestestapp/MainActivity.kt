package com.example.salestestapp

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.salestestapp.ui.home.navigation.homeScreen
import com.example.salestestapp.ui.home.navigation.homeScreenRoute
import com.example.salestestapp.ui.salescontrol.navigation.navigateToSalesControl
import com.example.salestestapp.ui.salescontrol.navigation.salesControlScreen
import com.example.salestestapp.ui.shimmer.navigation.cardPaymentScreen
import com.example.salestestapp.ui.shimmer.navigation.navigateToCardPaymentScreen
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = homeScreenRoute) {
                homeScreen(
                    onCard = { isSignature ->
                        navController.navigateToCardPaymentScreen(isSignature)
                    },
                    onSalesControl = { navController.navigateToSalesControl() },
                    onBack = { navController.popBackStack() }
                )
                cardPaymentScreen(
                    onBack = { navController.popBackStack() },
                    onAcceptSignature = { bitmap ->
                        saveBitmapAsPNG(
                            context = this@MainActivity,
                            bitmap = bitmap,
                            filename = "signature"
                        )
                    }
                )
                salesControlScreen(onBack = { navController.popBackStack() })
            }
        }
    }

    private fun saveBitmapAsPNG(context: Context, bitmap: Bitmap, filename: String) {
        val outputStream: OutputStream
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            // Use MediaStore API to save the image on devices running Android 10 (API level 29) or above
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val resolver = context.contentResolver
            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            outputStream = imageUri?.let { resolver.openOutputStream(it) }!!
        } else {
            // Use the traditional method to save the image on devices running below Android 10
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            outputStream = FileOutputStream(image)
        }

        // Compress the bitmap and save it to the output stream
        outputStream.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            it.flush()
        }

        // Notify the media scanner about the new image file so it appears in the gallery
        MediaScannerConnection.scanFile(context, arrayOf(outputStream.toString()), null, null)
    }
}