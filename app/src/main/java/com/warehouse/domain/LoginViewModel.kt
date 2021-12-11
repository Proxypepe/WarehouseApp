package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.*
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.remote.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


class LoginViewModel(private val repository: RequestRepository?, private val remoteRepository: AuthRepository?) : ViewModel() {

    private var email       = ""
    private var password    = ""
    val loading = MutableLiveData(false)
    val access = MutableLiveData(false)

    fun setFullState(email: String, password: String){
        this.email      = email
        this.password   = password
    }

    fun insertUser(user: UserDTO) {
            insert(user)
    }


    fun getUserByEmail(email: String): Flow<UserDTO>? {
        return repository?.getUserByEmail(email)
    }

    private fun insert(user: UserDTO) = viewModelScope.launch{
        loading.value = true
        val userFlow = repository?.getUserByEmailNullable(user.email)
        userFlow?.collect { value ->
            if (value == null)
            {
                repository?.insertUser(user)

            }
            loading.value = false
        }
    }

    fun checkAuth(){
        if (email != "" && password != "") {
            loading.value = true
            remoteRepository?.checkAuth(email, password)?.enqueue(object : Callback<UserDTO> {
                override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                    if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                        val req = response.body()
                        println(response.toString())
                        access.value = true
                        loading.value = false
                    } else {
                        Log.e("Auth Api", "None")
                        access.value = false
                        loading.value = false
                    }
                }

                override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                    Log.e("Auth Api", "Failed")
                    access.value = false
                    loading.value = false
                }
            })
        }

    }
}

class LoginViewModelFactory(private val repository: RequestRepository,
                            private val remoteRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository, remoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}