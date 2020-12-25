package com.example.newsapp.data

import androidx.paging.*
import com.example.newsapp.api.NewsService
import com.example.newsapp.data.dto.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(val service:NewsService,val database: AppDatabase) {


    //from network
    fun getNewsStreamFlow(query: String): Flow<PagingData<NewsItem>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NewsPagingSource.PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(service,query,database) }
        ).flow
    }


    //from database ...single source of truth

    fun getNewsStreamFlowFromDb(query: String): Flow<PagingData<NewsItem>> {

        // appending '%' so we can allow other characters to be before and after the query string
       // val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.newsDao().getNews() }

        return Pager(
            config = PagingConfig(pageSize = NewsPagingSource.PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = NewsRemoteMediator(
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}