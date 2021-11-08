package com.example.vinyls_jetpack_application.network

import android.content.Context
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
    }
    private var comments: HashMap<Int, List<Comment>> = hashMapOf<Int, List<Comment>>()
    //private var comments: ArrayMap<Int, List<Comment>> = arrayMapOf<Int, List<Comment>>()
    fun addComments(albumId: Int, comment: List<Comment>){
        if (comments.containsKey(albumId)){
            comments[albumId] = comment
        }
    }
    fun getComments(albumId: Int) : List<Comment>{
        return if (comments.containsKey(albumId)) comments[albumId]!! else listOf<Comment>()
    }

    /*private var comments: SparseArray<List<Comment>> = SparseArray()
    fun addComments(albumId: Int, comment: List<Comment>){
       if (comments[albumId]==null){
           comments.setValueAt(albumId, comment)
       }
    }
    fun getComments(albumId: Int) : List<Comment>{
       return if (comments[albumId]!=null) comments[albumId]!! else listOf<Comment>()
    }
    */

    /*private var comments: LruCache<Int, List<Comment>> = LruCache(3)
    fun addComments(albumId: Int, comment: List<Comment>){
        if (comments[albumId] == null){
            comments.put(albumId, comment)
        }
    }
    fun getComments(albumId: Int) : List<Comment>{
        return if (comments[albumId]!=null) comments[albumId]!! else listOf<Comment>()
    }*/
}