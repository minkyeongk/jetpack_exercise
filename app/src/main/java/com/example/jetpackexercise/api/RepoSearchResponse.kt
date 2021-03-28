package com.example.jetpackexercise.api

import com.example.jetpackexercise.model.Repo
import com.google.gson.annotations.SerializedName

/**
 * RepoSearchResponse
 * searchRepo API 호출의 응답을 저장하는 data class
 * 아이템 전체 개수와 아이템 리스트
 */

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)

