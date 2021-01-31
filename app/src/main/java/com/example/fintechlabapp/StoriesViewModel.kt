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

    private val _storyList = mutableListOf<Result<StoryResponse>>()
    private var _currentPage: Int = -1

    private val loadingExceptionHandler = CoroutineExceptionHandler {
        coroutineContext, exception ->
            println("CoroutineExceptionHandler got $exception in $coroutineContext")
            _storyInfo.value = Result.Error("")
    }

    fun getNextStory(){
        val nextPage = _currentPage + 1
        if (nextPage == _storyList.size){
            loadStory()
        }
        else if (nextPage < _storyList.size){
            _storyInfo.value = _storyList[nextPage]
        }
        _currentPage = nextPage
    }

    fun getPrevStory(){
        val prevPage = _currentPage - 1
        if (prevPage >= 0){
            _storyInfo.value = _storyList[prevPage]
            _currentPage = prevPage
        }
    }

    private fun loadStory(){
        viewModelScope.launch(loadingExceptionHandler) {
            _storyInfo.value = Result.Loading()

            val result = repository.getStory()
            if (result is Result.Success){
                _storyList.add(result)
            }
            _storyInfo.value = result
        }
    }

}