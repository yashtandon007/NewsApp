package com.example.newsapp.data.entities

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.dto.NewsItem

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsitem")
    fun getNews(): PagingSource<Int, NewsItem>

    //    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
//    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): LiveData<List<Plant>>
//
//    @Query("SELECT * FROM plants WHERE id = :plantId")
//    fun getPlant(plantId: String): LiveData<Plant>
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newslist: List<NewsItem>)

    @Query("DELETE FROM newsitem")
    suspend fun clearNews()
}