package com.example.puchs.domain.home

import com.example.puchs.core.Result
import com.example.puchs.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Result<List<Post>>
}