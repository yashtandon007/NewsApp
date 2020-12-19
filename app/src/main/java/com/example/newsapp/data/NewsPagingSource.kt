package com.example.newsapp.data

import androidx.paging.PagingSource
import com.example.newsapp.api.NewsService
import com.example.newsapp.data.dto.NewsItem

class NewsPagingSource(val service: NewsService):PagingSource<Int, NewsItem>(){

    val UNSPLASH_STARTING_PAGE_INDEX = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {

            println("yash tandon $page")
            val params: MutableMap<String, String> = HashMap()
            params["api-key"] = "51929e20-3d18-4bfc-aaa7-77c8b1eba27e"
            params["show-fields"] = "thumbnail,trailText"
            params["page"] = "$page"
            params["page-size"] = "${Companion.PAGE_SIZE}"

            val response = service.getNews(
                params
            )
            val news = response.response.results
            LoadResult.Page(
                data = news,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.response.pages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        val PAGE_SIZE = 9
    }

}