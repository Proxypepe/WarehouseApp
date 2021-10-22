package com.warehouse.repository.database.dao
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserAndRequestDTO
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RequestDao {

//    @Query("SELECT * FROM request")
//    fun getRequests(): Flow<List<RequestDTO>>
//
//    @Query("SELECT * FROM request WHERE id = :id")
//    fun getRequestById(id: Int): Flow<RequestDTO>
//
//    @Insert
//    suspend fun insert(request: RequestDTO)
//
//    @Update
//    suspend fun update(request: RequestDTO)
//
//    @Query("DELETE FROM request")
//    suspend fun deleteAll()
//
//    @Delete
//    suspend fun delete(request: RequestDTO)


    @Transaction
    @Query("SELECT * FROM User WHERE fullname = :fullName")
    abstract fun getDataByFullName(fullName: String) : Flow<UserAndRequestDTO>

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertUser(user: UserDTO)

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertRequest(requests: List<RequestDTO>)

    @Insert
    @Transaction
    suspend fun insertUserAndRequest(user: UserDTO, requests: List<RequestDTO>) {
        insertUser(user)
        val index = user.userID

        requests.forEach { it.userID = index}
        insertRequest(requests)
    }
}