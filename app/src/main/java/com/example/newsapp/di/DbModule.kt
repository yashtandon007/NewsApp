package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.api.NewsService
import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.entities.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DbModule {


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }

}

