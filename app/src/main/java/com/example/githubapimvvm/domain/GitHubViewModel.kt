package com.example.githubapimvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val result: T): Result<T>()
    data class Failure(val exception: Exception): Result<Nothing>()
}


class GitHubViewModel(val repository: GitHubRepository = GitHubRepositoryImpl()): ViewModel() {

    fun onCreate() {
        viewModelScope.launch {
            val result = repository.fetchGitHubUser()
            when(result) {
                is Result.Success -> { println(result.result.name) }
                is Result.Failure -> { println(result.exception) }
            }
        }
    }

}


