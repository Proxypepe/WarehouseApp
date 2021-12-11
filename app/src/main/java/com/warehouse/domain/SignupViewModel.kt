package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.User
import com.warehouse.repository.remote.repository.RegisterRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: RequestRepository?, private val remoteRepository: RegisterRepository): ViewModel(){

    private var fullName            = ""
    private var email               = ""
    private var password            = ""
    private var confirmedPassword   = ""


    fun setFullState(fullName: String, email: String,
                        password: String, confirmedPassword: String){
        this.fullName           = fullName
        this.email              = email
        this.password           = password
        this.confirmedPassword  = confirmedPassword
    }

    private fun comparePasswords(): Boolean = password == confirmedPassword

    fun insert(user: UserDTO) = viewModelScope.launch {
        repository?.insertUser(user)
    }
    fun insert(user: UserDTO, requests: List<RequestDTO>) = viewModelScope.launch {
        repository?.insert(user, requests)
    }

    fun createUser() {
        val user = User(fullName, email, password, "single_user")
        remoteRepository.createUser(user)
        Log.d("Create user", "Sent")
    }

}


class SignupViewModelFactory(private val repository: RequestRepository,
                             private val remoteRepository: RegisterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(repository, remoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}