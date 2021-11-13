package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinyls_jetpack_application.database.CommentsDao
import com.example.vinyls_jetpack_application.models.Comment
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class CommentsRepository (val application: Application, private val commentsDao: CommentsDao){

    suspend fun refreshData(albumId: Int): List<Comment>{
        var cached = commentsDao.getComments(albumId)
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getComments(albumId)
        } else cached
    }
    

}