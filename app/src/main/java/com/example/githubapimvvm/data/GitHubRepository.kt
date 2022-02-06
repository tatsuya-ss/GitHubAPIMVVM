package com.example.githubapimvvm

import com.example.githubapimvvm.domain.model.GitHubModel
import com.example.githubapimvvm.domain.mapper.convertToModel

interface GitHubRepository {
    suspend fun fetchGitHubUser(): Result<GitHubModel>
}

class GitHubRepositoryImpl  constructor(
    val client: GitHubDataStore = GitHubDataStoreImpl()
): GitHubRepository {

    override suspend fun fetchGitHubUser(): Result<GitHubModel> {
        val result = client.fetchGitHubUser()
        when(result) {
            is Result.Success -> {
                val gitHubModel = result.result.convertToModel()
                return Result.Success(gitHubModel)
            }
            is Result.Failure -> {
                return Result.Failure(result.exception)
            }
            else -> {
                return Result.Failure(error("例外"))
            }
        }
    }

}
