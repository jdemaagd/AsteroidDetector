package com.jdemaagd.asteroiddetector.api.database.entities

import android.os.Parcelable

import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.android.parcel.Parcelize

@Entity(tableName = "image_table")
@Parcelize
data class ImageEntity(@PrimaryKey(autoGenerate = true) val id: Long=0L,
                       val mediaType: String, val title: String, val url: String) : Parcelable