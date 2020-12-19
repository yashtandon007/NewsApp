package com.example.newsapp.data.dto

data class Response(
    val currentPage: Int,
    val orderBy: String,
    val pageSize: Int,
    val pages: Int,
    val results: List<NewsItem>,
    val startIndex: Int,
    val status: String,
    val total: Int,
    val userTier: String
)