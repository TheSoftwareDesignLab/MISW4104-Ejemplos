package com.example.vinyls_jetpack_application.network

import android.content.Context
import android.content.SharedPreferences
import android.util.SparseArray
import androidx.collection.ArrayMap
import androidx.collection.LruCache
import androidx.collection.arrayMapOf
import com.example.vinyls_jetpack_application.models.Comment

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
        const val APP_SPREFS = "com.example.vinyls_jetpack_application.app"
        const val ALBUMS_SPREFS = "com.example.vinyls_jetpack_application.albums"
        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }
    private var comments: HashMap<Int, List<Comment>> = hashMapOf<Int, List<Comment>>()

    fun addComments(albumId: Int, comment: List<Comment>){
        if (comments.containsKey(albumId)){
            comments[albumId] = comment
        }
    }
    fun getComments(albumId: Int) : List<Comment>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf<Comment>()
    }

}