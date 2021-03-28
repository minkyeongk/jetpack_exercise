package com.example.jetpackexercise.model

import com.google.gson.annotations.SerializedName

/**
 * 각각의 깃허브 repo를 저장할 immutable model data class
 * Github API 로부터 리스트 형태로 반환됨, 모든 필드에 직렬화된 이름과 함께 주석이 달려있음
 * Room repos table을 정의하는 클래스이고 기본키는 [id]
 */

data class Repo(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("html_url") val url: String,
    @field:SerializedName("stargazers_count") val stars: Int,
    @field:SerializedName("forks_count") val forks: Int,
    @field:SerializedName("language") val language: String?
)
