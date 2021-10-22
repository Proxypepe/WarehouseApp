package com.warehouse.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.remote.RemoteRepository

class SignupViewModel(private val repository: RequestRepository?): ViewModel(){

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



}


class SignupViewModelFactory(private val repository: RequestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}