package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: RequestRepository?) : ViewModel() {



    private var email       = ""
    private var password    = ""


    fun setFullState(email: String, password: String){
        this.email      = email
        this.password   = password
    }

    fun insertUser(user: UserDTO) {
        val userDTO = repository?.getUserByEmail(user.email)
        if (userDTO != null)
        {
            insert(user)
        }
    }


    fun getUserByEmail(email: String): Flow<UserDTO>? {
        return repository?.getUserByEmail(email)
    }

    private fun insert(user: UserDTO) = viewModelScope.launch{
        repository?.insertUser(user)
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