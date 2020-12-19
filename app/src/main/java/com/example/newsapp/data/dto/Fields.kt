package com.example.newsapp.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fields(
    val trailText: String,
    val thumbnail: String
): Parcelable