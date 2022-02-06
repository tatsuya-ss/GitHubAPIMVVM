package com.example.githubapimvvm.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapimvvm.GitHubViewModel
import com.example.githubapimvvm.R
import com.example.githubapimvvm.databinding.ActivityMainBinding
import com.example.githubapimvvm.domain.model.GitHubModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GitHubViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupLiveData()
        viewModel.onCreate()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun setupRecyclerView() {
        binding.RecyclerView.adapter = RecyclerViewAdapter(listOf<GitHubModel>())
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupLiveData() {
        viewModel.gitHubUserLiveData.observe(this) {
            val recyclerView = binding.RecyclerView.adapter as RecyclerViewAdapter
            recyclerView.reload(it)
        }
    }

}