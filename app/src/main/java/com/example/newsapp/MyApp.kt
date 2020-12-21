package com.example.newsapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.newsapp.util.ThemeHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val isDarkModeActive = sharedPreferences.getBoolean(ThemeHelper.DARK_MODE,false)
        if(isDarkModeActive){
            ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
        }else{
            ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
        }


    }
}