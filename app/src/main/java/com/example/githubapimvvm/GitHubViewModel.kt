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

class GitHubModel(val name: String)
data class GitHubEntity(val name: String)

interface GitHubClientRetrofit {
    @GET("users/tatsuya-ss")
    suspend fun fetchUser(): GitHubEntity
}

interface GitHubRepository {
    suspend fun fetchGitHubUser(): Result<GitHubModel>
}

class GitHubRepositoryImpl(val client: GitHubClient = GitHubClientImpl()): GitHubRepository {

    override suspend fun fetchGitHubUser(): Result<GitHubModel> {
        val result = client.fetchGitHubUser()
        when(result) {
            is Result.Success -> {
                val gitHubModel = convertToModel(result.result)
                return Result.Success(gitHubModel)
            }
            is Result.Failure -> {
                return Result.Failure(result.exception)
            }
        }
    }

    private fun convertToModel(entity: GitHubEntity): GitHubModel {
        return GitHubModel(entity.name)
    }

}

interface GitHubClient {
    suspend fun fetchGitHubUser(): Result<GitHubEntity>
}

class GitHubClientImpl: GitHubClient {

    private fun makeOkHttp(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor{ chain ->
            val request = chain.request().newBuilder().build()
            val reponse = chain.proceed(request)
            return@addInterceptor reponse
        }
        return httpClient
    }

    private fun createService(): GitHubClientRetrofit {
        val client = makeOkHttp().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
        val githubClient = retrofit.create(GitHubClientRetrofit::class.java)
        return githubClient
    }

    override suspend fun fetchGitHubUser(): Result<GitHubEntity> {
        return try {
            val result = createService().fetchUser()
            Result.Success(result)
        } catch (error: Exception) {
            Result.Failure(error)
        }
    }

}
