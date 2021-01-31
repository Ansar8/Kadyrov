package com.example.fintechlabapp

import com.example.fintechlabapp.api.StoryResponse

interface IStoryRepository {
    suspend fun getStory(): Result<StoryResponse>
}