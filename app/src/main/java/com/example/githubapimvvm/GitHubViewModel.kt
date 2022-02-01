package com.example.githubapimvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubViewModel: ViewModel() {

    fun onCreate() {
        viewModelScope.launch {
            fetchGitHubUser()
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
            .client(client)
            .build()
        val githubClient = retrofit.create(GitHubClient::class.java)
        return githubClient
    }

    private fun fetchGitHubUser() {
        createService().fetchUser().enqueue(object : retrofit2.Callback<GitHubEntity> {
            override fun onResponse(call: Call<GitHubEntity>, response: Response<GitHubEntity>) {
                println(response.body().toString())
            }

            override fun onFailure(call: Call<GitHubEntity>, t: Throwable) {
                println(t.message)
            }
        })
    }

}
