package com.example.newsapp.api

import com.example.newsapp.data.dto.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsService {

    @GET("search")
    suspend fun getNews(
        @QueryMap params: Map<String, String>
    ): NewsResponse

    companion object {
        const val BASE_URL = "https://content.guardianapis.com/"
        fun create(): NewsService {

            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)

        }
    }


}