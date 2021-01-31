package com.example.fintechlabapp

import com.example.fintechlabapp.api.StoriesApi
import com.example.fintechlabapp.api.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoryRepository(private val storiesApi: StoriesApi): IStoryRepository {

    override suspend fun getRandomStory(): Result<StoryResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = storiesApi.getRandomStory()
                Result.Success(response)
            }
            catch (t: Throwable) {
                Result.Error("Oops..looks like network failure!")
            }
        }

    override suspend fun getStoriesByType(type: String, page: Int): Result<StoryResponse> =
        withContext(Dispatchers.IO) {
            try {
                val stories = storiesApi.getStories(type, page).stories
                val response = stories.first() //TODO: replace with rundom number
                Result.Success(response)
            }
            catch (t: Throwable) {
                Result.Error("Oops..looks like network failure!")
            }
        }
}