package com.example.githubapimvvm.domain.mapper

import com.example.githubapimvvm.GitHubEntity
import com.example.githubapimvvm.domain.model.GitHubModel

fun GitHubEntity.convertToModel(): GitHubModel {
    return GitHubModel(name)
}