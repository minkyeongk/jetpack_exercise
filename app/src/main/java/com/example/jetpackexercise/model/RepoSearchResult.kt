package com.example.jetpackexercise.model

import java.lang.Exception

/**
 * RepoSearchResult
 * Success시 반환된 검색 결과 리스트
 * Error시 state
 */

sealed class RepoSearchResult {
    data class Success(val data: List<Repo>) : RepoSearchResult()
    data class Error(val error: Exception) : RepoSearchResult()
}
