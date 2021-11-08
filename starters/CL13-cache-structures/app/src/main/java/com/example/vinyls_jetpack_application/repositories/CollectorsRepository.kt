package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Collector
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class CollectorsRepository (val application: Application){
    suspend fun refreshData(): List<Collector> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

}