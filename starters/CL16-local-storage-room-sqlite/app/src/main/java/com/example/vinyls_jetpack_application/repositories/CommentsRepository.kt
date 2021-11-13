package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.Cache
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Comment
import com.example.vinyls_jetpack_application.network.CacheManager
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class CommentsRepository (val application: Application){
    val format = Json {  }

    suspend fun refreshData(albumId: Int): List<Comment>{
        var comments = getComments(albumId)
        return if(comments.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                comments = NetworkServiceAdapter.getInstance(application).getComments(albumId)
                addComments(albumId, comments)
                comments
            }
        } else comments
    }


    suspend fun getComments(albumId:Int): List<Comment>{
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                Log.d("deserialize", resp.toString())
                return format.decodeFromString<List<Comment>>(storedVal)
            }
        }
        return listOf<Comment>()
    }
    suspend fun addComments(albumId:Int, comments: List<Comment>){
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }

}