package com.example.fintechlabapp.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryResponse(
    val id: Int,
    @SerialName("description")
    val title: String,
    @SerialName("gifURL")
    val pictureUrl: String
)