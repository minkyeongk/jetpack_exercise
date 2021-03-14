package com.example.jetpackexercise.Data.repository.github

class GithubRepository {
    private val githubClient = GithubService.client
    suspend fun getRepositories(query: String) = githubClient?.getRepositories(query)
}


