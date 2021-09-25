package com.warehouse.domain


import androidx.lifecycle.*
import com.warehouse.repository.RequestRepository
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Contact
import kotlinx.coroutines.launch
import java.util.*
import kotlinx.coroutines.flow.Flow

data class State(val productName: String, val amount: String, val warehousePlace: String, val status: String)

class RequestViewModel(private val repository: RequestRepository): ViewModel() {

    val allRequests: LiveData<List<RequestDTO>> = repository.allRequests.asLiveData()

    private var productName: String?    = null
    private var amount: Int?            = null
    private var warehousePlace: Int?    = null
    private var status: String?         = null
    private var arrivalDate: Date?      = null
    private var contact: Contact?       = null
    private var currentState: State?    = null
    private var request: RequestDTO?    = null


    private fun initRequest(productName: String, amount: Int, warehousePlace: Int,
                            status: String, arrivalDate: Date?, contact: Contact?){
        request = RequestDTO(productName=productName, amount=amount, warehousePlace=warehousePlace,
            status=status, arrivalDate=arrivalDate, contact=contact)
    }

    private fun insert(request: RequestDTO) = viewModelScope.launch {
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
        initRequest(productName, amount, warehousePlace, status, arrivalDate, contact)
    }

    fun writeRequest(){
        this.request?.let { insert(it) }
        contact = null
    }

    fun setContact(contact: Contact) {
        this.contact = contact
    }

    fun saveState(productName: String, amount: String, warehousePlace: String, status: String){
        currentState = State(productName, amount, warehousePlace, status)
    }

    fun getRequestById(id: Int): Flow<RequestDTO> {
        return repository.getRequestById(id)
    }

    fun getState(): State? {
        return currentState
    }

    fun getRequest(): RequestDTO? {
        return request
    }

    fun clear() {
        currentState = null
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