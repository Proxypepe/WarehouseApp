package com.warehouse.repository

import android.app.Application
import com.warehouse.repository.database.RequestRoomDatabase


class RequestsApplication : Application() {

    val database by lazy { RequestRoomDatabase.getDatabase(this) }
    val repository by lazy { RequestRepository(database.requestDao()) }
}
