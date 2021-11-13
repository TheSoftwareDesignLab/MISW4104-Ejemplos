package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.database.AlbumsDao
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class AlbumRepository (val application: Application, private val albumsDao: AlbumsDao){
    suspend fun refreshData(): List<Album>{
        var cached = albumsDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getAlbums()
        } else cached
    }
}