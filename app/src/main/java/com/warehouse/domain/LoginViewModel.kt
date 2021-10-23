package com.warehouse.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.flow.Flow


class LoginViewModel(private val repository: RequestRepository?) : ViewModel() {



    private var email       = ""
    private var password    = ""


    fun setFullState(email: String, password: String){
        this.email      = email
        this.password   = password
    }



    fun getUserByEmail(email: String): Flow<UserDTO>? {
        return repository?.getUserByEmail(email)
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