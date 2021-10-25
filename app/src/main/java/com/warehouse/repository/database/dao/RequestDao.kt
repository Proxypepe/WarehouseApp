package com.warehouse.repository.database.dao
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserAndRequestDTO
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RequestDao {

    @Transaction
    @Query("SELECT * FROM User WHERE fullname = :fullName")
    abstract fun getDataByFullName(fullName: String) : Flow<UserDTO>

    @Transaction
    @Query("SELECT * FROM User WHERE email = :email")
    abstract fun getUserByEmail(email: String) : Flow<UserDTO>

    @Transaction
    @Query("SELECT * FROM Request WHERE userID = :userID")
    abstract fun getRequestsByUserID(userID: Int) : Flow<List<RequestDTO>>

    @Transaction
    @Query("SELECT * FROM User WHERE userID = :userID")
    abstract fun getUserById(userID: Int) : Flow<UserDTO>

    @Query("SELECT * FROM Request WHERE requestID = :requestID")
    abstract fun getRequestById(requestID: Int) : Flow<RequestDTO>

    @Transaction
    @Query("SELECT * FROM User")
    abstract fun getUsers() : Flow<List<UserDTO>>

    @Transaction
    @Query("SELECT * FROM Request")
    abstract fun getRequests() : Flow<List<RequestDTO>>

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertUser(user: UserDTO)

    @Insert
    abstract suspend fun insertRequest(request: RequestDTO)

    @Insert
    abstract suspend fun insertRequests(requests: List<RequestDTO>)

    @Insert
    @Transaction
    suspend fun insertUserAndRequest(user: UserDTO, requests: List<RequestDTO>) {
        insertUser(user)
        val index = user.userID

        requests.forEach { it.userID = index}
        insertRequests(requests)
    }

    @Insert
    @Transaction
    suspend fun insertRequests(user: UserDTO, requests: List<RequestDTO>) {
        val index = user.userID

        requests.forEach { it.userID = index}
        insertRequests(requests)
    }

    @Update
    abstract suspend fun updateUser(user: UserDTO)

    @Update
    abstract suspend fun updateRequest(request: RequestDTO)
}