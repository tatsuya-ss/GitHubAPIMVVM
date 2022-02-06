package com.example.githubapimvvm.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapimvvm.GitHubViewModel
import com.example.githubapimvvm.R
import com.example.githubapimvvm.databinding.ActivityMainBinding
import com.example.githubapimvvm.domain.GitHubModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GitHubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupViewModel()
        viewModel.onCreate()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun setupRecyclerView() {
        binding.RecyclerView.adapter = RecyclerViewAdapter(listOf<GitHubModel>(GitHubModel("俺様")))
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        viewModel = GitHubViewModel()
    }

}