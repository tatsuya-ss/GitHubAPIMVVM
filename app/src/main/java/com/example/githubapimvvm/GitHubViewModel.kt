package com.example.githubapimvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val result: T): Result<T>()
    data class Failure(val exception: Exception): Result<Nothing>()
}

class GitHubViewModel: ViewModel() {

    fun onCreate() {
        viewModelScope.launch {
            val result = fetchGitHubUser()
            when(result) {
                is Result.Success -> { println(result.result.name) }
                is Result.Failure -> { println(result.exception) }
            }
        }
    }

    private fun makeOkHttp(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor{ chain ->
            val request = chain.request().newBuilder().build()
            val reponse = chain.proceed(request)
            return@addInterceptor reponse
        }
        return httpClient
    }

    private fun createService(): GitHubClient {
        val client = makeOkHttp().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
        val githubClient = retrofit.create(GitHubClient::class.java)
        return githubClient
    }

    private suspend fun fetchGitHubUser(): Result<GitHubEntity> {
        return try {
            val result = createService().fetchUser()
            Result.Success(result)
        } catch (error: Exception) {
            Result.Failure(error)
        }
    }

}
