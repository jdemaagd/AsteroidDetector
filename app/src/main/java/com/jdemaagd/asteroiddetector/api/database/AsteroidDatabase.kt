package com.jdemaagd.asteroiddetector.api.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.jdemaagd.asteroiddetector.api.database.dao.AsteroidDao
import com.jdemaagd.asteroiddetector.api.database.dao.ImageDao
import com.jdemaagd.asteroiddetector.api.database.entities.AsteroidEntity
import com.jdemaagd.asteroiddetector.api.database.entities.ImageEntity

@Database(entities = [AsteroidEntity::class, ImageEntity::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val imageDao: ImageDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids").build()
        }
    }
    return INSTANCE
}