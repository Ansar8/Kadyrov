package com.example.fintechlabapp.repository

import android.content.Context
import com.example.fintechlabapp.R
import com.example.fintechlabapp.utils.Result
import com.example.fintechlabapp.api.StoriesApi
import com.example.fintechlabapp.api.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoryRepository(
    private val context: Context,
    private val storiesApi: StoriesApi
): IStoryRepository {

    override suspend fun getRandomStory(): Result<StoryResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = storiesApi.getRandomStory()
                Result.Success(response)
            }
            catch (t: Throwable) {
                Result.Error(context.getString(R.string.error_network_failure))
            }
        }

    override suspend fun getStoriesByType(type: String, page: Int): Result<StoryResponse> =
        withContext(Dispatchers.IO) {
            try {
                val stories = storiesApi.getStories(type, page).stories
                if (stories.isEmpty()){
                    Result.Error(context.getString(R.string.error_no_content))
                }
                else{
                    val response = stories.first()
                    Result.Success(response)
                }
            }
            catch (t: Throwable) {
                Result.Error(context.getString(R.string.error_network_failure))
            }
        }
}