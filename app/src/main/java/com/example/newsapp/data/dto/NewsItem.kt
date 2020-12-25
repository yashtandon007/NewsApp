package com.example.newsapp.data.dto

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class NewsItem(
    val apiUrl: String,
    @PrimaryKey()
    val id: String,
    val isHosted: Boolean,
    val pillarName: String,
    val sectionId: String,
    val sectionName: String,
    val type: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    @Embedded val fields:Fields
):Parcelable