package com.example.githubapimvvm

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
