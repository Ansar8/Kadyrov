package com.example.fintechlabapp

import com.example.fintechlabapp.api.StoryResponse
import retrofit2.http.Path

interface IStoryRepository {
    suspend fun getRandomStory(): Result<StoryResponse>
    suspend fun getStoriesByType(type: String, page: Int): Result<StoryResponse>
}