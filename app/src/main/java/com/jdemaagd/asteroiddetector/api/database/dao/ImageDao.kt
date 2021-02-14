package com.jdemaagd.asteroiddetector.api.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.jdemaagd.asteroiddetector.api.database.entities.ImageEntity

@Dao
interface ImageDao{
    @Query("select * from image_table")
    fun getImageOfDay() : LiveData<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg imageEntity: ImageEntity)

    @Query("delete from image_table")
    fun  clear()
}