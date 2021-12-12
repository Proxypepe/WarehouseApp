package com.warehouse.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.presentation.activity.SignUpInActivity
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.User
import com.warehouse.repository.remote.repository.RegisterRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val repository: RequestRepository?, private val remoteRepository: RegisterRepository): ViewModel(){

    private var fullName            = ""
    private var email               = ""
    private var password            = ""
    private var confirmedPassword   = ""


    fun setFullState(fullName: String, email: String,
                        password: String, confirmedPassword: String, ){
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

    fun createUser(context: Context) {
        val user = User(fullName, email, password, "single_user")
        remoteRepository.createUser(user).enqueue(object : Callback<UserDTO>{
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                if (response.isSuccessful && response.code() == 200 && response.body() != null)
                {
                    println(response.body())
                    val intent = Intent(context, MainActivity::class.java).apply {
                        response.body()?.let { it1 ->
                            Toast.makeText(context, "${it1.userID}", LENGTH_LONG).show()
                            putExtra("userId", it1.userID)
                            putExtra("role", it1.role)}
                    }
                    (context as SignUpInActivity).startActivity(intent)
                } else {
                    Log.e("Post create user", "${response.code()}")
                }
            }
            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Toast.makeText(context, "Check ur Internet connection", LENGTH_LONG).show()
            }
        })
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