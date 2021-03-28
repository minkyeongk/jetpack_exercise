package com.example.jetpackexercise.data

import android.util.Log
import com.example.jetpackexercise.api.GithubService
import com.example.jetpackexercise.api.IN_QUALIFIER
import com.example.jetpackexercise.model.Repo
import com.example.jetpackexercise.model.RepoSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

/**
 * Repository class that works with local and remote data sources.
 */

class GithubRepository(private val service: GithubService) {

    // 받은 모든 결과를 저장하는 리스트
    private val inMemoryCache = mutableListOf<Repo>()

    // shared flow of results
    // 최신 결과를 받아오기 위한 업데이트 통신 flow
    private val searchResults = MutableSharedFlow<RepoSearchResult>(replay = 1)

    // 마지막으로 요청된 페이지 저장, 요청이 성공할 경우, 페이지 넘버 증가시킴
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    // 동시에 여러 요청이 발생하는 것을 막아줌
    private var isRequestInProgress = false

    /**
     * 코루틴을 활용한 네트워크 호출
     * 쿼리에 맞는 repositories 검색
     * 네트워크에서 데이터 더 가져올 때마다(중간함수) 데이터 스트림으로 노출
     */
    suspend fun getSearchResultStream(query: String): Flow<RepoSearchResult> {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry(query: String) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    // 호출 받아온 데이터 저장
    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        val apiQuery = query + IN_QUALIFIER
        try {
            val response = service.searchRepos(apiQuery, lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d("GithubRepository", "response $response")

            val repos = response.items ?: emptyList()
            inMemoryCache.addAll(repos)
            val reposByName = reposByName(query)
            searchResults.emit(RepoSearchResult.Success(reposByName))   // 검색 결과 방출
            successful = true
        }
        catch (exception: IOException) {
            searchResults.emit(RepoSearchResult.Error(exception))
        }
        catch (exception: HttpException) {
            searchResults.emit(RepoSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<Repo> {
        // 메모리 캐시에서 입력과 맞는 repo들 검색
        return inMemoryCache.filter {
            it.name.contains(query, true) ||
                    (it.description != null && it.description.contains(query, true))
        }.sortedWith(compareByDescending<Repo> { it.stars }.thenBy { it.name })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}
