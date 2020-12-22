package com.example.newsapp

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.dto.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(val repo: NewsRepository): ViewModel() {


    fun getData(query:String): Flow<PagingData<NewsItem>> {
        return repo.getNewsStreamFlow(query)
    }


}