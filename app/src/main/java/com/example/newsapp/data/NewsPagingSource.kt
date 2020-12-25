package com.example.newsapp.data

import android.util.Log
import androidx.paging.PagingSource
import com.example.newsapp.HomeViewModel
import com.example.newsapp.api.NewsService
import com.example.newsapp.data.dto.NewsItem

class NewsPagingSource(val service: NewsService, val query: String,val db: AppDatabase):PagingSource<Int, NewsItem>(){

    val UNSPLASH_STARTING_PAGE_INDEX = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {

            Log.e(HomeViewModel.TAG, "query and page $query $page")
            val params: MutableMap<String, String> = HashMap()
            params["api-key"] = "51929e20-3d18-4bfc-aaa7-77c8b1eba27e"
            params["show-fields"] = "thumbnail,trailText"
            params["page"] = "$page"
            params["page-size"] = "${Companion.PAGE_SIZE}"
            if(!query.equals("home")){
                params["section"] = query
            }

            val response = service.getNews(
                params
            )
            val news = response.response.results
            db.newsDao().insertAll(news)
            LoadResult.Page(

                data = news,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.response.pages) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("yashtandon", "load: "+exception.cause )
            LoadResult.Error(exception)
        }
    }

    companion object {
        val PAGE_SIZE = 10
    }

}