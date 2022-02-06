package com.example.githubapimvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapimvvm.domain.model.GitHubModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.Exception
import javax.inject.Inject

sealed class Result<out R> {
    data class Success<out T>(val result: T): Result<T>()
    data class Failure(val exception: Exception): Result<Nothing>()
}

class GitHubViewModel @ViewModelInject constructor(
    val repository: GitHubRepository = GitHubRepositoryImpl()
): ViewModel() {

    private val _gitHubUserLiveData: MutableLiveData<List<GitHubModel>> = MutableLiveData()
    val gitHubUserLiveData: LiveData<List<GitHubModel>> = _gitHubUserLiveData

    fun onCreate() {
        viewModelScope.launch {
            val result = repository.fetchGitHubUser()
            when(result) {
                is Result.Success -> {
                    println(result.result.name)
                    _gitHubUserLiveData.value = listOf(result.result)
                }
                is Result.Failure -> { println(result.exception) }
            }
        }
    }

}


