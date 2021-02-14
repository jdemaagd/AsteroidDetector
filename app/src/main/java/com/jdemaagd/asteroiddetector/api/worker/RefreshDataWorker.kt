package com.jdemaagd.asteroiddetector.api.worker

import android.content.Context

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.jdemaagd.asteroiddetector.api.repositories.AsteroidRepository
import com.jdemaagd.asteroiddetector.api.repositories.ImageRepository
import com.jdemaagd.asteroiddetector.api.database.getDatabase

import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val asteroidRepository = AsteroidRepository(database)
        val imageRepository = ImageRepository(database)

        return try {
            imageRepository.refreshImageOfDay()

            asteroidRepository.refreshAsteroids()
            asteroidRepository.removeOldAsteroids()
            Result.success()
        }
        catch (e: HttpException) {
            Result.retry()
        }
    }
}