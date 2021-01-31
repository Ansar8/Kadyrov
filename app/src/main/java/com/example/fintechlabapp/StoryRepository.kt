package com.example.fintechlabapp

import com.example.fintechlabapp.api.StoriesApi
import com.example.fintechlabapp.api.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoryRepository(private val storiesApi: StoriesApi): IStoryRepository {

    override suspend fun getStory(): Result<StoryResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = storiesApi.getRandomStory()
                Result.Success(response)
            }
            catch (t: Throwable) {
                Result.Error("Oops..looks like network failure!")
            }
        }
}