package com.bangkit.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.event.api.ApiConfig
import com.bangkit.event.response.EventResponse
import com.bangkit.event.response.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val upComing = MutableLiveData<List<ListEventsItem>>()
    val upcoming: LiveData<List<ListEventsItem>> get() = upComing

    private val finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedevents: LiveData<List<ListEventsItem>> get() = finishedEvents

    private val Loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = Loading

    private val error = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = error

    init {
        fetchEvents(1)
        fetchEvents(0)
    }

    fun fetchEvents(active: Int) {
        Loading.value = true
        error.value = null
        ApiConfig.create().getEvents(active).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                Loading.value = false
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    if (active == 1) upComing.value = events
                    else finishedEvents.value = events
                } else {
                    error.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Loading.value = false
                error.value = "Failed to fetch data: ${t.message}"
            }
        })
    }
}
