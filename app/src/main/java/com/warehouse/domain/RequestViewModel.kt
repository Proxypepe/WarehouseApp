package com.warehouse.domain

import androidx.lifecycle.*
import com.warehouse.repository.RequestRepository
import com.warehouse.repository.database.entity.RequestDTO
import kotlinx.coroutines.launch
import java.util.*

class RequestViewModel(private val repository: RequestRepository): ViewModel() {

    val allRequest: LiveData<List<RequestDTO>> = repository.allRequests.asLiveData()

    private var productName: String?    = null
    private var amount: Int?            = null
    private var warehousePlace: Int?    = null
    private var status: String?         = null
    private var arrivalDate: Date?      = null
    private var request: RequestDTO?       = null


    private fun initRequest(productName: String, amount: Int, warehousePlace: Int,
                            status: String, arrivalDate: Date?){
        request = RequestDTO(productName=productName, amount=amount, warehousePlace=warehousePlace,
            status=status, arrivalDate=arrivalDate)
    }

    fun insert(request: RequestDTO) = viewModelScope.launch {
        repository.insert(request)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun setRequest(productName: String, amount: Int, warehousePlace: Int,
                    status: String, arrivalDate: Date?) {
        this.productName        = productName
        this.amount             = amount
        this.warehousePlace     = warehousePlace
        this.status             = status
        this.arrivalDate        = arrivalDate
        initRequest(productName, amount, warehousePlace, status, arrivalDate)
    }

    fun writeRequest(){
        this.request?.let { insert(it) }
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