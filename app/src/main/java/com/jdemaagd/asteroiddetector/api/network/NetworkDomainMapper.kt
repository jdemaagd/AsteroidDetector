package com.jdemaagd.asteroiddetector.api.network

import com.jdemaagd.asteroiddetector.api.network.models.NetworkAsteroid
import com.jdemaagd.asteroiddetector.api.network.models.NetworkImage
import com.jdemaagd.asteroiddetector.models.Asteroid
import com.jdemaagd.asteroiddetector.models.ImageOfDay

fun List<Asteroid>.toNetworkModel():List<NetworkAsteroid> {

    return map{ asteroid ->
        NetworkAsteroid(
            id = asteroid.id,
            codename = asteroid.codename,
            closeApproachDate = asteroid.closeApproachDate,
            absoluteMagnitude = asteroid.absoluteMagnitude,
            estimatedDiameter = asteroid.estimatedDiameter,
            relativeVelocity = asteroid.relativeVelocity,
            distanceFromEarth = asteroid.distanceFromEarth,
            isPotentiallyHazardous = asteroid.isPotentiallyHazardous
        )
    }
}

fun List<NetworkAsteroid>.toDomainModel():List<Asteroid> {
    return map{ networkAsteroid ->
        Asteroid(
            id = networkAsteroid.id,
            codename = networkAsteroid.codename,
            closeApproachDate = networkAsteroid.closeApproachDate,
            absoluteMagnitude = networkAsteroid.absoluteMagnitude,
            estimatedDiameter = networkAsteroid.estimatedDiameter,
            relativeVelocity = networkAsteroid.relativeVelocity,
            distanceFromEarth = networkAsteroid.distanceFromEarth,
            isPotentiallyHazardous = networkAsteroid.isPotentiallyHazardous
        )
    }
}

fun NetworkImage.toDomainModel(): ImageOfDay {
    return ImageOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}

fun ImageOfDay.toDomainModel(): NetworkImage {
    return NetworkImage(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}