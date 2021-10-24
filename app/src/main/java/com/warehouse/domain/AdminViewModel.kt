package com.warehouse.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: RequestRepository): ViewModel() {
    val allData = repository.getUsers()

    fun updateUser(user: UserDTO, role: String) = viewModelScope.launch {
        val newRecord  = UserDTO(userID = user.userID,
            fullname = user.fullname, email = user.email, password =  user.password, role =  role)
        repository.updateUser(newRecord)
    }
    
    

}



class AdminViewModelFactory(private val repository: RequestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}