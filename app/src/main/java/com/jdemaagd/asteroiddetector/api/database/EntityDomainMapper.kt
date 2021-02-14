package com.jdemaagd.asteroiddetector.api.database

import com.jdemaagd.asteroiddetector.api.database.entities.AsteroidEntity
import com.jdemaagd.asteroiddetector.api.database.entities.ImageEntity
import com.jdemaagd.asteroiddetector.models.Asteroid
import com.jdemaagd.asteroiddetector.models.ImageOfDay

fun ArrayList<Asteroid>.toDatabaseModel():Array<AsteroidEntity> {

    return map { asteroid ->
        AsteroidEntity(
            id = asteroid.id,
            codename = asteroid.codename,
            closeApproachDate = asteroid.closeApproachDate,
            absoluteMagnitude = asteroid.absoluteMagnitude,
            estimatedDiameter = asteroid.estimatedDiameter,
            relativeVelocity = asteroid.relativeVelocity,
            distanceFromEarth = asteroid.distanceFromEarth,
            isPotentiallyHazardous = asteroid.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun List<AsteroidEntity>.toDomainModel():List<Asteroid> {
    return map { asteroidEntity ->
        Asteroid(
            id = asteroidEntity.id,
            codename = asteroidEntity.codename,
            closeApproachDate = asteroidEntity.closeApproachDate,
            absoluteMagnitude = asteroidEntity.absoluteMagnitude,
            estimatedDiameter = asteroidEntity.estimatedDiameter,
            relativeVelocity = asteroidEntity.relativeVelocity,
            distanceFromEarth = asteroidEntity.distanceFromEarth,
            isPotentiallyHazardous = asteroidEntity.isPotentiallyHazardous
        )
    }
}

fun ImageOfDay.toDatabaseModel():ImageEntity {
    return ImageEntity(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}

fun ImageEntity.toDomainModel(): ImageOfDay {
    return ImageOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}
