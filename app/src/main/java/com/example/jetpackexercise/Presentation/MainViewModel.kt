package com.example.jetpackexercise.Presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackexercise.Data.model.GithubRepositoryModel
import com.example.jetpackexercise.Data.repository.github.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/* coroutine 활용하여 비동기로 API 호출
*  받아온 결과를 LiveDatadp post */

class MainViewModel(private val githubRepository: GithubRepository) : ViewModel()
{
    private val _githubRepositories = MutableLiveData<List<GithubRepositoryModel>>()
    val githubRepositories = _githubRepositories
    fun requestGithubRepositories(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            githubRepository.getRepositories(query)?.let { response ->
                if(response.isSuccessful) {
                    response.body()?.let {
                        _githubRepositories.postValue(it.items)
                    }
                }
            }
        }
    }
}

