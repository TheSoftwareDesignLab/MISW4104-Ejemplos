package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Comment
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class CommentsRepository (val application: Application){
    suspend fun refreshData(albumId: Int): List<Comment> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getComments(albumId)
    }
}