package com.example.vinyls_jetpack_application

import android.app.Application
import com.example.vinyls_jetpack_application.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}