package com.jdemaagd.asteroiddetector.main

import android.app.Application
import androidx.lifecycle.*

import com.jdemaagd.asteroiddetector.api.repositories.AsteroidRepository
import com.jdemaagd.asteroiddetector.api.repositories.ImageRepository
import com.jdemaagd.asteroiddetector.api.database.getDatabase
import com.jdemaagd.asteroiddetector.models.Asteroid
import com.jdemaagd.asteroiddetector.common.Event

import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val imageRepository = ImageRepository(database)
    private val asteroidRepository = AsteroidRepository(database)

    private val _navigateToDetails = MutableLiveData<Event<Asteroid>>()

    val navigateToDetails: LiveData<Event<Asteroid>>
        get() = _navigateToDetails

    init {
        viewModelScope.launch {
            imageRepository.refreshImageOfDay()
            asteroidRepository.refreshAsteroids()
        }
    }

    val imageOfDay = imageRepository.imageOfDay
    val asteroids = asteroidRepository.asteroids

    fun onApplyFilter(filter : AsteroidRepository.FilterType) {
        asteroidRepository.applyFilter(filter)
    }

    fun asteroidClicked(asteroid: Asteroid){
        _navigateToDetails.value = Event(asteroid)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}