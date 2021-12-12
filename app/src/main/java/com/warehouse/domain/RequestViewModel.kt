package com.warehouse.domain


import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.*
import com.warehouse.repository.remote.repository.RemoteRequestRepository
import com.warehouse.repository.remote.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


data class State(val productName: String, val amount: String, val warehousePlace: String,
                 val status: String, val price_value: String)

class RequestViewModel(private val repository: RequestRepository,
                       private val requestRepository: RemoteRequestRepository,
                        private val userRepository: UserRepository
): ViewModel() {
    var userId: Int? = null
    var role: String? = null
    var userData: LiveData<UserDTO>? = null
    var allRequests: LiveData<List<RequestDTO>>? = null
    var allRequestsCache: LiveData<List<RequestDTO>>? = null

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


    var gotFromRemote                   = false

    private fun initRequest(productName: String, amount: Int, warehousePlace: Int,
                            status: String, arrivalDate: Date?, contact: Contact?, price: Price?){
        request = userId?.let {
            RequestDTO(productName=productName, amount=amount, warehousePlace=warehousePlace,
                status=status, arrivalDate=arrivalDate, contact=contact, price=price, userID = it
            )
        }
    }

    private fun insert(request: RequestDTO) = viewModelScope.launch {
        repository.insert(request)
    }

    fun update(request: RequestDTO) =  viewModelScope.launch{
        repository.updateRequest(request)
    }


    private fun setUpCache(){
        if (role == "admin" || role == "moderator")
        {
            allRequestsCache = repository.getRequests().asLiveData()
        } else
        {
            userId?.let { allRequestsCache = repository.getRequestsByUserID(it).asLiveData() }
        }
    }

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

    fun getRequestsByUserId(userId: Int){
        this.userId = userId
        gotFromRemote = true
        setUpCache()
        requestRepository.getRequestsByUserID(userId).enqueue(object: Callback<List<RequestDTO>>{
            override fun onResponse(call: Call<List<RequestDTO>>,
                                    response: Response<List<RequestDTO>>
            ) {
                if (response.isSuccessful && response.body() != null && response.code() == 200)
                {
                    Log.d("Request List", "${response.body()}")
                    allRequests = MutableLiveData(response.body())
                } else {
                    allRequests = allRequestsCache
                }
            }

            override fun onFailure(call: Call<List<RequestDTO>>, t: Throwable) {
                allRequests = allRequestsCache
            }
        })
    }

    fun makeRequest(productName: String, amount: Int, warehousePlace: Int,
                    status: String, arrivalDate: Date?, price: Double) {
            val newReq = userId?.let {
                val priceTmp = Price(price, priceBase?: "USD")
                RequestModel(
                    it, productName, amount, warehousePlace, status,
                    contact, priceTmp)
            }
        if (newReq != null) {
            requestRepository.addNewRequest(newReq ).enqueue(object: Callback<RequestResponseModel>{
                override fun onResponse(call: Call<RequestResponseModel>, response: Response<RequestResponseModel>) {
                    if(response.isSuccessful && response.code() == 200 && response.body() != null)
                    {
                        val answer = response.body()!!
                        println(response.body())
                        cacheRequest(answer.requestID, answer.productName, answer.amount,
                            answer.warehousePlace, answer.status, null, answer.price?:
                            Price(0.0, "RUB"))
                        allRequests = allRequestsCache
                    }
                }

                override fun onFailure(call: Call<RequestResponseModel>, t: Throwable) {
                    cacheRequest(0, productName, amount,
                        warehousePlace, status, null,
                        Price(price, priceBase?: "RUB"))
                }
            })
        }
    }
    private fun cacheRequest(requestId: Int = 0,productName: String, amount: Int, warehousePlace:Int,
                status: String, date: Date?, price: Price) {
        request = userId?.let { RequestDTO(requestId, it, productName, amount, warehousePlace,
            status, date,contact, price) }
        writeRequest()
        clear()
    }
    fun writeRequest(){
        this.request?.let { insert(it) }
        contact = null
    }

    fun setContact(contact: Contact) {
        this.contact = contact
    }

    fun saveState(productName: String, amount: String, warehousePlace: String, status: String, price: String){
        currentState = State(productName, amount, warehousePlace, status,  price)
    }

    fun getRequestById(id: Int): Flow<RequestDTO> {
        return repository.getRequestById(id)
    }

    fun getPriceBase(): String? {
        return priceBase
    }

    fun getState(): State? {
        return currentState
    }


    fun reCreateRequestByPrice(requestDTO: RequestDTO, price: Price) {
        val newRequest = RequestDTO(
            requestDTO.requestID,
            requestDTO.userID,
            requestDTO.productName,
            requestDTO.amount,
            requestDTO.warehousePlace,
            requestDTO.status,
            requestDTO.arrivalDate,
            requestDTO.contact,
            price
            )
        request = newRequest
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
class RequestViewModelFactory(private val repository: RequestRepository,
                              private val remoteRepository: RemoteRequestRepository,
                              private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestViewModel(repository, remoteRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}