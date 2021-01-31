package com.example.fintechlabapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fintechlabapp.api.StoriesApi
import com.example.fintechlabapp.repository.StoryRepository

class StoriesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        StoriesViewModel::class.java -> StoriesViewModel(
            StoryRepository(
                context,
                StoriesApi.create()
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}