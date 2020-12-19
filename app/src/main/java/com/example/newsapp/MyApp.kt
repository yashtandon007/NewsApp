package com.example.newsapp

import android.app.Application
import com.example.newsapp.util.ThemeHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeHelper.applyTheme(ThemeHelper.DEFAULT_MODE)
    }
}