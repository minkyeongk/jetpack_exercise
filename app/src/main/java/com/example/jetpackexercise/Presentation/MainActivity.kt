package com.example.jetpackexercise.Presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackexercise.Data.model.GithubRepositoryModel
import com.example.jetpackexercise.Data.repository.github.GithubRepository
import com.example.jetpackexercise.Presentation.GithubRepository.GithubRepositoryAdapter
import com.example.jetpackexercise.Presentation.GithubRepository.GithubRepositoryItemDecoration
import com.example.jetpackexercise.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    class MainActivity : AppCompatActivity() {
        private lateinit var viewModel: MainViewModel
        private lateinit var viewModelFactory: MainViewModelFactory
        private lateinit var mGithubRepositoryAdapter: GithubRepositoryAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            Log.d("bind", "bind")
            initButton()
            initViewModel()
        }

        private fun initButton() {
            search_btn.setOnClickListener { onSearchClick() }
        }

        private fun initViewModel() {
            viewModelFactory = MainViewModelFactory(GithubRepository())
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.githubRepositories.observe(this) {
                updateRepositories(it)
            }
        }

        private fun updateRepositories(repos: List<GithubRepositoryModel>) {
            if (::mGithubRepositoryAdapter.isInitialized) {
                mGithubRepositoryAdapter.update(repos)
            } else {
                mGithubRepositoryAdapter = GithubRepositoryAdapter(repos).apply {
                    listener = object : GithubRepositoryAdapter.OnGithubRepositoryClickListener {
                        override fun onItemClick(position: Int) {
                            mGithubRepositoryAdapter.getItem(position).run {
                                openGithub(htmlUrl)
                            }
                        }
                    }
                }
                item_list.run {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = mGithubRepositoryAdapter
                    addItemDecoration(GithubRepositoryItemDecoration(6, 6))
                }
            }
        }

        private fun openGithub(url: String) {
            try {
                val uri = Uri.parse(url)
                Intent(Intent.ACTION_VIEW, uri).run {
                    startActivity(this)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun onSearchClick() {
            search_input.run {
                viewModel.requestGithubRepositories(search_input.text.toString())
                text.clear()
                hideKeyboard()
            }
        }

        private fun hideKeyboard() {
            currentFocus?.run {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }
}
