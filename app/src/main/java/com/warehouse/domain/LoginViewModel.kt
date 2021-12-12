package com.warehouse.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.presentation.activity.SignUpInActivity
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.UserModel
import com.warehouse.repository.remote.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val repository: RequestRepository?, private val remoteRepository: AuthRepository?) : ViewModel() {

    private var email       = ""
    private var password    = ""
    private var context: Context? = null
    val loading = MutableLiveData(false)
    val access = MutableLiveData(false)

    fun setFullState(email: String, password: String, context: Context){
        this.email      = email
        this.password   = password
        this.context    = context
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



    fun checkAuth() {
        if (email != "" && password != "") {
            loading.value = true
            remoteRepository?.checkAuth(email, password)?.enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                        val req = response.body()
                        Log.d("Request", response.body().toString())
                        access.value = true
                        loading.value = false
                        if(context != null) {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                req?.let { it1 ->
                                    putExtra("userId", it1.userId)
                                    putExtra("email", it1.email)
                                    putExtra("role", it1.role)
                                }
                            }
                            context?.startActivity(intent)
                        }
                    } else {
                        Log.e("Auth Api", "None")
                        Toast.makeText(context, "Uncorrect password or email", Toast.LENGTH_LONG).show()
                        access.value = false
                        loading.value = false
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    //
                    Log.e("Auth Api", "Failed")
                    access.value = false
                    loading.value = false
                    var user: UserDTO? = null
                    var access: Boolean = false
                    getUserByEmail(email)?.asLiveData()?.observe(context as (SignUpInActivity), {
                        if (it.email == email && it.password == password)
                        {
                            user = it
                            access = true
                        } else {
                            user = null
                            access = false
                            Toast.makeText(context, "Uncorrect Email or password", Toast.LENGTH_LONG).show()
                        }
                        if (access)
                        {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                user?.let { it1 ->
                                    Toast.makeText(context, "${it1.userID}", Toast.LENGTH_LONG).show()
                                    putExtra("userId", it1.userID)
                                    putExtra("role", it1.role)}

                            }
                            (context as SignUpInActivity).startActivity(intent)
                        }
                    })
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