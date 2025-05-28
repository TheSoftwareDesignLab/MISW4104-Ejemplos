package com.example.jetpack_codelab.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jetpack_codelab.models.Collector
import com.example.jetpack_codelab.models.Comment
import com.example.jetpack_codelab.network.NetworkServiceAdapter
import java.io.IOException

class CommentsViewModel (application: Application, albumId: Int) :  AndroidViewModel(application) {


    /**
     * A playlist of videos that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _comments = MutableLiveData<List<Comment>>()

    /**
     * A playlist of videos that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    val comments: LiveData<List<Comment>>
        get() = _comments

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    /**
     * init{} is called immediately when this ViewModel is created.
     */
    val id:Int = albumId

    init {
        refreshDataFromNetwork()
    }

    /**
     * Refresh data from network and pass it via LiveData. Use a coroutine launch to get to
     * background thread.
     */
    private fun refreshDataFromNetwork() {
        Log.d("netparam",id.toString())

        NetworkServiceAdapter.getInstance(getApplication()).getComments(id,{
            _comments.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun printByRating(lower:Int, upper:Int){
        var stringBuffer = StringBuffer()
        if(!_comments.value.isNullOrEmpty()){
            for (i in 0 until _comments.value!!.size) {
                if (_comments.value!!.get(i).rating.toInt() < upper && _comments.value!!.get(i).rating.toInt() > lower) {
                    val string = _comments.value!!.get(i).description
                    stringBuffer.append(string + "\n")
                }
            }
        }
        Log.d("result", "Comentarios en rating: [ $lower , $upper ]: ${stringBuffer.toString()}")
    }


    fun printListOfCommentsStartingUpper(){
        var list = mutableListOf<String>()
        if(!_comments.value.isNullOrEmpty()){
            for (i in 0 until _comments.value!!.size) {
                val comment = _comments.value!!.get(i).description
                if (comment.toCharArray()[0].isUpperCase()) {
                    list.add(comment)
                }
            }
        }
        Log.d("result", "Comentarios con mayúscula:"+list.toString())
    }


    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentsViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}