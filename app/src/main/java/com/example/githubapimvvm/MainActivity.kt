package com.example.githubapimvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapimvvm.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class GitHubModel(val name: String)
data class GitHubEntity(val name: String)
interface GitHubClient{
    @GET("users/tatsuya-ss")
    fun fetchUser(): Call<GitHubEntity>
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()

        GlobalScope.launch {
            launch {
                fetchGitHubUser()
            }
        }

    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun setupRecyclerView() {
        binding.RecyclerView.adapter = RecyclerViewAdapter(listOf<GitHubModel>(GitHubModel("俺様")))
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
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

class GitHubViewModel: ViewModel() {

}