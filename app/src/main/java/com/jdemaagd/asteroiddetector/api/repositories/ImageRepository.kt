package com.jdemaagd.asteroiddetector.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

import com.jdemaagd.asteroiddetector.api.database.AsteroidDatabase
import com.jdemaagd.asteroiddetector.api.database.toDatabaseModel
import com.jdemaagd.asteroiddetector.api.database.toDomainModel
import com.jdemaagd.asteroiddetector.api.network.Network
import com.jdemaagd.asteroiddetector.api.network.toDomainModel
import com.jdemaagd.asteroiddetector.models.ImageOfDay

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import timber.log.Timber

class ImageRepository(private val database: AsteroidDatabase) {

    val imageOfDay: LiveData<ImageOfDay> =
        Transformations.map(database.imageDao.getImageOfDay()) { imageEntity ->
            imageEntity?.toDomainModel()
        }

    suspend fun refreshImageOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val image = Network.imageOfDayService.getImageOfDay()
                val domainImage = image.toDomainModel()
                Timber.i("image=$domainImage")
                if (domainImage.mediaType == "image") {
                    database.imageDao.clear()
                    database.imageDao.insertAll(domainImage.toDatabaseModel())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Timber.d("Refresh failed ${e.message}")
                }
                e.printStackTrace()
            }
        }
    }
}