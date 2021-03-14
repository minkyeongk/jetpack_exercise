package com.example.jetpackexercise.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackexercise.Data.repository.github.GithubRepository

/* 생성자에 파라미터가 있는 뷰모델 초기화를 위한 ViewModelFactory */

class MainViewModelFactory(private val githubRepository: GithubRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GithubRepository::class.java).newInstance(githubRepository)
    }
}

