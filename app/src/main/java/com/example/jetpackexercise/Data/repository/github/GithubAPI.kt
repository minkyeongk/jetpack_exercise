package com.example.jetpackexercise.Data.repository.github

import com.example.jetpackexercise.Data.model.GithubRepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {
    @GET("search/repositories")
    suspend fun getRepositories(    // 'suspend' keyword for coroutine
        @Query("q") query: String
    ): Response<GithubRepoModel>

}