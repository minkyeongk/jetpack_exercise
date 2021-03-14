package com.example.jetpackexercise.Data.repository.github

import com.example.jetpackexercise.Data.repository.BaseService

/* GithubAPI 활용하여 GithubService
*  싱글턴 패턴 구현을 위해 object로 작성 */

object GithubService {
    private const val GITHUB_URL = "http://api.github.com"
    val client = BaseService()
        .getClient(GITHUB_URL)?.create(
        GithubAPI::class.java)
}

