package com.example.jetpackexercise.Data.model

import com.google.gson.annotations.SerializedName

/* Github API의 Json 응답 모델 */

data class GithubRepoModel(
        @SerializedName("total_count")
        val totalCount: Int,

        @SerializedName("items")
        val items: List<GithubRepositoryModel>
)

data class GithubRepositoryModel(
        @SerializedName("id")
        val id: Long,

        @SerializedName("full_name")
        val fullName: String,

        @SerializedName("html_url")
        val htmlUrl: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("stargazers_count")
        val stargazersCount: Int,

        @SerializedName("owner")
        val owner: GithubRepositoryOwnerModel
)

data class GithubRepositoryOwnerModel(
        @SerializedName("avatar_url")
        val avatarUrl: String
)

