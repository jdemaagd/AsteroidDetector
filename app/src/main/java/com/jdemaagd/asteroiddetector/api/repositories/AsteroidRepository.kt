package com.jdemaagd.asteroiddetector.api.repositories

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

import com.jdemaagd.asteroiddetector.api.database.AsteroidDatabase
import com.jdemaagd.asteroiddetector.api.database.toDatabaseModel
import com.jdemaagd.asteroiddetector.api.database.toDomainModel
import com.jdemaagd.asteroiddetector.api.network.Network
import com.jdemaagd.asteroiddetector.api.network.getOneWeekAheadDateFormatted
import com.jdemaagd.asteroiddetector.api.network.getTodayDateFormatted
import com.jdemaagd.asteroiddetector.api.network.parseAsteroidsJsonResult
import com.jdemaagd.asteroiddetector.models.Asteroid

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import org.json.JSONObject

import timber.log.Timber

import java.time.LocalDate

class AsteroidRepository (private val database: AsteroidDatabase)
{
    enum class FilterType {WEEKLY, TODAY, SAVED}

    private val _filterType = MutableLiveData(FilterType.WEEKLY)

    val filterType : LiveData<FilterType>
        get() = _filterType

    @RequiresApi(Build.VERSION_CODES.O)
    private val _startDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val _endDate = _startDate.plusDays(7)

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroids : LiveData<List<Asteroid>> = Transformations.switchMap(filterType)
    {
        when(it){
            FilterType.WEEKLY ->
                Transformations.map(database.asteroidDao.getWeeklyAsteroids(_startDate.toString(), _endDate.toString())) { asteroidEntities ->
                    asteroidEntities.toDomainModel()
                }

            FilterType.TODAY ->
                Transformations.map(database.asteroidDao.getTodaysAsteroids(_startDate.toString())) {asteroidEntities ->
                    asteroidEntities.toDomainModel()
                }

            FilterType.SAVED ->
                Transformations.map(database.asteroidDao.getSavedAsteroids()) { asteroidEntities ->
                    asteroidEntities.toDomainModel()
                }

            else -> throw IllegalArgumentException("")
        }
    }

    fun applyFilter(filterType: FilterType){
        _filterType.value = filterType
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try{
                val startDate = getTodayDateFormatted()
                val endDate = getOneWeekAheadDateFormatted()
                val asteroidsResult  = Network.asteroidService.getAsteroids(startDate , endDate)
                val parsedAsteroids = parseAsteroidsJsonResult(JSONObject(asteroidsResult))
                database.asteroidDao.insertAll(*parsedAsteroids.toDatabaseModel())
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    Timber.d("Refresh failed ${e.message}")
                }
                e.printStackTrace()
            }
        }
    }

    suspend fun removeOldAsteroids(){
        withContext(Dispatchers.IO){
            database.asteroidDao.removeOldAsteroids(getTodayDateFormatted())
        }
    }
}