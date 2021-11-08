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
import org.json.JSONArray

class CommentsRepository (val application: Application){
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
        var a = mutableListOf<Comment>()
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")
            if(!storedVal.isNullOrBlank()){
                val resp = JSONArray(storedVal)
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    a.add(i, Comment(albumId = albumId, rating = item.getInt("rating").toString(), description = item.getString("description")))
                }
            }
        }
        return a
    }
    suspend fun addComments(albumId:Int, comments: List<Comment>){
        val prefs = CacheManager.getPrefs(application.baseContext, CacheManager.ALBUMS_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = JSONArray(comments).toString()
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }

}