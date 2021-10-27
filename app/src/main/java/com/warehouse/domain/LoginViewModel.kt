package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.*
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Thread.sleep


class LoginViewModel(private val repository: RequestRepository?) : ViewModel() {



    private var email       = ""
    private var password    = ""
    val loading = MutableLiveData(false)

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
}

class LoginViewModelFactory(private val repository: RequestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}