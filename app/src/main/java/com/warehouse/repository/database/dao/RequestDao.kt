package com.warehouse.repository.database.dao
import androidx.room.*
import com.warehouse.repository.database.entity.Request
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    @Query("SELECT * FROM request")
    fun getRequests(): Flow<List<Request>>

    @Insert
    suspend fun insert(request: Request)

    @Update
    suspend fun update(request: Request)
    
    @Query("DELETE FROM request")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(request: Request)
}