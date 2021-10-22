package com.warehouse.domain


import androidx.lifecycle.*
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Contact
import com.warehouse.repository.model.Price
import kotlinx.coroutines.launch
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class State(val productName: String, val amount: String, val warehousePlace: String,
                 val status: String, val price_value: String)

class RequestViewModel(private val repository: RequestRepository): ViewModel() {

    var tmp = MutableLiveData<List<RequestDTO>>()
    val allRequests: LiveData<List<RequestDTO>> = tmp

    private var productName: String?    = null
    private var amount: Int?            = null
    private var warehousePlace: Int?    = null
    private var status: String?         = null
    private var arrivalDate: Date?      = null
    private var contact: Contact?       = null
    private var price: String?          = null
    private var priceBase: String?      = null
    private var currentState: State?    = null
    private var request: RequestDTO?    = null


    private fun initRequest(productName: String, amount: Int, warehousePlace: Int,
                            status: String, arrivalDate: Date?, contact: Contact?, price: Price?){
        request = RequestDTO(productName=productName, amount=amount, warehousePlace=warehousePlace,
            status=status, arrivalDate=arrivalDate, contact=contact, price=price, userID = 0)
    }

//    private fun insert(request: RequestDTO) = viewModelScope.launch {
//        repository.insert(request)
//    }

//    private fun update(request: RequestDTO) = viewModelScope.launch {
//        repository.update(request)
//    }
    private fun update(request: RequestDTO) =  viewModelScope.launch{

    }

//    fun deleteAll() = viewModelScope.launch {
//        repository.deleteAll()
//    }

    fun setRequest(productName: String, amount: Int, warehousePlace: Int,
                    status: String, arrivalDate: Date?, price: String) {
        this.productName        = productName
        this.amount             = amount
        this.warehousePlace     = warehousePlace
        this.status             = status
        this.arrivalDate        = arrivalDate
        this.price              = price
        val p = price.toDouble()
        val b = priceBase ?: "RUB"
        initRequest(productName, amount, warehousePlace, status, arrivalDate,
            contact, Price(p, b))
    }

    fun writeRequest(){
//        this.request?.let { insert(it) }
//        contact = null
    }

    fun setContact(contact: Contact) {
        this.contact = contact
    }

    fun saveState(productName: String, amount: String, warehousePlace: String, status: String, price: String){
        currentState = State(productName, amount, warehousePlace, status,  price)
    }

    fun getRequestById(id: Int): Flow<RequestDTO> = flow {

    }


//    {
//        return repository.getRequestById(id)
//    }

    fun getPriceBase(): String? {
        return priceBase
    }

    fun getState(): State? {
        return currentState
    }


    fun reCreateRequestByPrice(requestDTO: RequestDTO, price: Price) {
//        val newRequest = RequestDTO(
//            requestDTO.requestID,
//            requestDTO.productName,
//            requestDTO.amount,
//            requestDTO.warehousePlace,
//            requestDTO.status,
//            requestDTO.arrivalDate,
//            requestDTO.contact,
//            price
//            )
//        request = newRequest
    }


    fun setPriceBase(base: String) {
        this.priceBase = base
    }


    fun update() {
        request?.let { update(it) }
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