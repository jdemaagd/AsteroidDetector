package com.jdemaagd.asteroiddetector.api.network.models

import com.squareup.moshi.Json

data class NetworkImage(@Json(name = "media_type") val mediaType: String, val title: String, val url: String)
