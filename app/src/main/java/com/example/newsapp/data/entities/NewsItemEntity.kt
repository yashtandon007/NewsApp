package com.example.newsapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class NewsItemEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
