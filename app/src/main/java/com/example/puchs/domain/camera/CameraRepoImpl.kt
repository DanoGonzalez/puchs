package com.example.puchs.domain.camera

import android.graphics.Bitmap
import com.example.puchs.data.remote.camera.CameraDataSource
import com.example.puchs.domain.camera.CameraRepo

class CameraRepoImpl(private val dataSource: CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)
    }
}