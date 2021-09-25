package com.warehouse.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.warehouse.repository.database.dao.RequestDao
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.utils.DateConverter


@Database(entities = [RequestDTO::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class  RequestRoomDatabase : RoomDatabase() {
    abstract fun requestDao(): RequestDao

    companion object {

        @Volatile
        private var INSTANCE: RequestRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): RequestRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RequestRoomDatabase::class.java,
                    "request"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}