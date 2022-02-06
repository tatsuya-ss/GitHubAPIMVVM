package com.example.githubapimvvm

import com.example.githubapimvvm.domain.GitHubModel

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
            else -> {
                return Result.Failure(error("例外"))
            }
        }
    }

    private fun convertToModel(entity: GitHubEntity): GitHubModel {
        return GitHubModel(entity.name)
    }

}
