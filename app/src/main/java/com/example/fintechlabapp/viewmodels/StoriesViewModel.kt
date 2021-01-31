package com.example.fintechlabapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechlabapp.api.StoryResponse
import com.example.fintechlabapp.repository.IStoryRepository
import com.example.fintechlabapp.utils.Result
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
            _storyInfo.value = Result.Error("Something went wrong when trying to load!")
    }

    fun getPrevStory(){
        val prevPage = _currentPage - 1
        if (prevPage >= 0){
            _storyInfo.value = _storyList[prevPage]
            _currentPage = prevPage
        }
    }

    fun getNextStory(type: String){
        val nextPage = _currentPage + 1
        if (nextPage == _storyList.size){
            loadStory(type, nextPage)
        }
        else if (nextPage < _storyList.size){
            _storyInfo.value = _storyList[nextPage]
            _currentPage = nextPage
        }
    }

    private fun loadStory(type: String, page: Int){
        viewModelScope.launch(loadingExceptionHandler) {
            _storyInfo.value = Result.Loading()

            val result =
                if (type == "random")
                    { repository.getRandomStory() }
                else
                    { repository.getStoriesByType(type, page) }
            if (result is Result.Success){
                _storyList.add(result)
                _currentPage += 1
            }
            _storyInfo.value = result
        }
    }

}