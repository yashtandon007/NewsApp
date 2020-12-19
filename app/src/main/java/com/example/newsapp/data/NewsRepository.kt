package com.example.newsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.api.NewsService
import com.example.newsapp.data.dto.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(val service:NewsService) {

    fun getNewsStreamFlow(): Flow<PagingData<NewsItem>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NewsPagingSource.PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(service) }
        ).flow
    }
}