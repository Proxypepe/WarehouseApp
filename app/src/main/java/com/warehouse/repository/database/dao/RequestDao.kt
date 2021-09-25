package com.warehouse.repository.database.dao
import androidx.room.*
import com.warehouse.repository.database.entity.RequestDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    @Query("SELECT * FROM request")
    fun getRequests(): Flow<List<RequestDTO>>

    @Query("SELECT * FROM request WHERE id = :id")
    fun getRequestById(id: Int): Flow<RequestDTO>

    @Insert
    suspend fun insert(request: RequestDTO)

    @Update
    suspend fun update(request: RequestDTO)
    
    @Query("DELETE FROM request")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(request: RequestDTO)
}