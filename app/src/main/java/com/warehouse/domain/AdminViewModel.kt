package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.UserModel
import com.warehouse.repository.remote.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewModel(private val repository: RequestRepository, private val userRepository: UserRepository): ViewModel() {
    val allData = repository.getUsers()

    fun updateUser(user: UserDTO, role: String) = viewModelScope.launch {
        val newRecord  = UserDTO(userID = user.userID,
            fullname = user.fullname, email = user.email, password =  user.password, role =  role)
        repository.updateUser(newRecord)
    }
    
    fun getAllUsers() {
        userRepository.getAllUsers().enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                    val req = response.body()
                    println(response.toString())

                } else {
                    Log.e("UserApi", "None")
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Log.e("UserApi", "Failed")
            }
        })
    }
}



class AdminViewModelFactory(private val repository: RequestRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminViewModel(repository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}