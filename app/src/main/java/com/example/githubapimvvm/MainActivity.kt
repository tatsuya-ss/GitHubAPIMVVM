package com.example.githubapimvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapimvvm.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.http.GET

class GitHubModel(val name: String)
data class GitHubEntity(val name: String)
interface GitHubClient{
    @GET("users/tatsuya-ss/")
    fun fetchUser(): Call<GitHubEntity>
}

//https://api.github.com/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()

    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun setupRecyclerView() {
        binding.RecyclerView.adapter = RecyclerViewAdapter(listOf<GitHubModel>(GitHubModel("俺様")))
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
    }

}

class GitHubViewModel: ViewModel() {

}