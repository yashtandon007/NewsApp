package com.example.newsapp

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.dto.NewsItem
import com.example.newsapp.data.entities.NewsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class HomeViewModel @ViewModelInject constructor(val repo: NewsRepository,val newsDao: NewsDao) : ViewModel() {

    var result: Flow<PagingData<NewsItem>>? = null

    companion object {
        val TAG = "yash"
    }

    init {
        Log.e(TAG, "viewmodel init...")
        //result =  loadData("home").asLiveData()
        result = loadData("home")
    }

    fun loadData(query: String): Flow<PagingData<NewsItem>> {
        return repo.getNewsStreamFlowFromDb(query).cachedIn(viewModelScope)
    }


    fun flowTest(): Flow<Int> = flow {
        emit(1)
    }

    fun flowConsume() {
        viewModelScope.launch {
            flowTest()
        }
    }


}