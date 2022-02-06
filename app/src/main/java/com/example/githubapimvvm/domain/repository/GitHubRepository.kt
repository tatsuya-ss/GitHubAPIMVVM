package com.example.githubapimvvm.domain.repository

import com.example.githubapimvvm.Result
import com.example.githubapimvvm.domain.model.GitHubModel

interface GitHubRepository {
    suspend fun fetchGitHubUser(): Result<GitHubModel>
}
