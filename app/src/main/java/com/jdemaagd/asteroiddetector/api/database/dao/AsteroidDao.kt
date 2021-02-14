package com.jdemaagd.asteroiddetector.api.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.jdemaagd.asteroiddetector.api.database.entities.AsteroidEntity

@Dao
interface AsteroidDao {
    @Query("select * from asteroid_table order by date(closeApproachDate) asc")
    fun getSavedAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("select * from asteroid_table where closeApproachDate = :date")
    fun getTodaysAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Query("select * from asteroid_table where closeApproachDate between :startDate and :endDate order by date(closeApproachDate) asc")
    fun getWeeklyAsteroids(startDate: String, endDate: String) : LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("delete from asteroid_table where closeApproachDate < :date")
    suspend fun removeOldAsteroids(date: String)
}