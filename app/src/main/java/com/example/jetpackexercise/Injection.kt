package com.example.jetpackexercise

import androidx.lifecycle.ViewModelProvider
import com.example.jetpackexercise.api.GithubService
import com.example.jetpackexercise.data.GithubRepository
import com.example.jetpackexercise.ui.ViewModelFactory

/**
 * Injection
 * 오브젝트를 생성하는 클래스
 * 오브젝트는 constructors의 인자로 전달되어 필요할 때 테스트를 위해 대체된다
 */
object Injection {
    /**
     * [GithubService]와 [GithubLocalCache]를 통해 [GithubRepository] instance 생성
     */

    private fun provideGithubRepository(): GithubRepository {
        return GithubRepository(GithubService.create())
    }

    /**
     * [ViewModelProvider.Factory] 제공, [ViewModel] objects의 레퍼런스로 활용된다.
     */

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}
