package com.example.fintechlabapp.repository

import com.example.fintechlabapp.utils.Result
import com.example.fintechlabapp.api.StoryResponse

interface IStoryRepository {
    suspend fun getRandomStory(): Result<StoryResponse>
    suspend fun getStoriesByType(type: String, page: Int): Result<StoryResponse>
}