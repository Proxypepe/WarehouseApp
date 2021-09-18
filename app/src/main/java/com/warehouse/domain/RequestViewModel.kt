package com.warehouse.domain

import androidx.lifecycle.*
import com.warehouse.repository.RequestRepository
import com.warehouse.repository.database.entity.Request
import kotlinx.coroutines.launch

class RequestViewModel(private val repository: RequestRepository): ViewModel() {

    val allRequest: LiveData<List<Request>> = repository.allRequests.asLiveData()

    fun insert(request: Request) = viewModelScope.launch {
        repository.insert(request)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class RequestViewModelFactory(private val repository: RequestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}