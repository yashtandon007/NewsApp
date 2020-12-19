package com.example.newsapp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.dto.NewsItem

class HomeViewModel @ViewModelInject constructor(val repo: NewsRepository): ViewModel() {


    fun getNewsList(query:String): LiveData<PagingData<NewsItem>> {
        var liveData = repo.getNewsStreamFlow(query).asLiveData()
        return liveData
    }

}