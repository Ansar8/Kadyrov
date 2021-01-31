package com.example.fintechlabapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fintechlabapp.api.StoriesApi

class StoriesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        StoriesViewModel::class.java -> StoriesViewModel( StoryRepository( StoriesApi.create()) )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}