package com.example.puchs.domain.home

import com.example.puchs.core.Result
import com.example.puchs.data.model.Post
import com.example.puchs.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatestPosts(): Result<List<Post>> = dataSource.getLatestPosts()
}