package com.example.fintechlabapp.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryListResponse(
    @SerialName("result")
    val stories: List<StoryResponse>
)
