package com.warehouse.domain

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private var email       = ""
    private var password    = ""


    fun setFullState(email: String, password: String){
        this.email      = email
        this.password   = password
    }
}