package com.jdemaagd.asteroiddetector.api.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.jdemaagd.asteroiddetector.BuildConfig.API_KEY
import com.jdemaagd.asteroiddetector.common.Constants.BASE_URL
import com.jdemaagd.asteroiddetector.api.network.models.NetworkImage

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date")startDate:String,
                     @Query("end_date")endDate:String,
                     @Query("api_key")apiKey: String = API_KEY): String
}

interface ImageOfDayService {

    @GET("planetary/apod")
    suspend fun getImageOfDay(@Query("api_key") apiKey:String = API_KEY): NetworkImage
}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory( MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()

object Network {
    val asteroidService = retrofit.create(AsteroidApiService::class.java)
    val imageOfDayService = retrofit.create(ImageOfDayService::class.java)
}
