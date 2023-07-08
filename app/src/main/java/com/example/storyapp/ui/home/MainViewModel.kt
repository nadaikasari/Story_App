package com.example.storyapp.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.data.local.UserModel
import com.example.storyapp.data.local.UserPreference
import com.example.storyapp.data.remote.network.ApiConfig
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.data.remote.response.StoriesResponse
import com.example.storyapp.di.Injection
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MainViewModel @Inject constructor(storyRepository: StoryRepository) : ViewModel()  {

//    private val _listStory = MutableLiveData<StoriesResponse>()
//    val getListStory: LiveData<StoriesResponse> = _listStory
//
//    private val _message = MutableLiveData<String>()
//    val getMessage: LiveData<String> = _message
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    fun getStory(token: String) {
//        _isLoading.value = true
//
//        val client = ApiConfig.getApiService().getAllStories("Bearer $token")
//        client.enqueue(object : Callback<StoriesResponse> {
//            override fun onResponse(
//                call: Call<StoriesResponse>,
//                response: Response<StoriesResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _listStory.value = response.body()
//                } else {
//                    _message.value = response.message()
//                }
//            }
//            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
//                _isLoading.value = false
//                _message.value = t.message.toString()
//            }
//        })
//    }
//
//    fun getDataUser(): LiveData<UserModel> {
//        return pref.getUser().asLiveData()
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            pref.logout()
//        }
//    }

    val isLoading: LiveData<Boolean> = storyRepository.isLoading

    val listStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)
}