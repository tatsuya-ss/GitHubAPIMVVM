package com.example.githubapimvvm

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.Exception

interface GitHubClientRetrofit {
    @GET("users/tatsuya-ss")
    suspend fun fetchUser(): GitHubEntity
}

interface GitHubDataStore {
    suspend fun fetchGitHubUser(): Result<GitHubEntity>
}

class GitHubDataStoreImpl: GitHubDataStore {

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
