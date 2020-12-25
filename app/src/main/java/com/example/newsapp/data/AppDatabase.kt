package com.example.newsapp.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsapp.data.dto.Fields
import com.example.newsapp.data.dto.NewsItem
import com.example.newsapp.data.entities.NewsDao
import com.example.newsapp.data.entities.NewsItemEntity
import com.example.newsapp.data.entities.RemoteKeys
import com.example.newsapp.data.entities.RemoteKeysDao

@Database(entities = [NewsItem::class, RemoteKeys::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "NewsDb")
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }



}