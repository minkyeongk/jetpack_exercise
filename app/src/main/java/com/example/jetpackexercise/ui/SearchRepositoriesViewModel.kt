package com.example.jetpackexercise.ui

import androidx.lifecycle.*
import com.example.jetpackexercise.data.GithubRepository
import com.example.jetpackexercise.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * SearchRepositoriesViewModel
 * [SearchRepoMainActivity]를 위한 ViewModel
 * [GithubRepository]와 함께 데이터를 불러온다
 */

class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    val repoResult: LiveData<RepoSearchResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultStream(queryString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    /**
     * 쿼리에 맞는 repository 검색
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}
