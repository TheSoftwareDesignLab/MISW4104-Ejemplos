package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.database.CollectorsDao
import com.example.vinyls_jetpack_application.models.Collector
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class CollectorsRepository (val application: Application, private val collectorsDao: CollectorsDao){
    suspend fun refreshData(): List<Collector>{
        var cached = collectorsDao.getCollectors()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getCollectors()
        } else cached
    }
}