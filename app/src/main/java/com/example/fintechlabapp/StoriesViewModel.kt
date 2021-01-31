package com.example.fintechlabapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechlabapp.api.StoryResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class StoriesViewModel(private val repository: IStoryRepository): ViewModel() {

    private val _storyInfo = MutableLiveData<Result<StoryResponse>>()
    val storyInfo: LiveData <Result<StoryResponse>> = _storyInfo

    private val loadingExceptionHandler = CoroutineExceptionHandler {
        coroutineContext, exception ->
            println("CoroutineExceptionHandler got $exception in $coroutineContext")
            _storyInfo.value = Result.Error("")
    }

    fun loadStory(){
        viewModelScope.launch(loadingExceptionHandler) {
            _storyInfo.value = Result.Loading()

            val story = repository.getStory()
            _storyInfo.value = story
        }
    }

}