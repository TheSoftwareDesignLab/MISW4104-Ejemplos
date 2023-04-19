package com.example.jetpack_codelab.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.jetpack_codelab.models.Album
import com.example.jetpack_codelab.models.Collector
import com.example.jetpack_codelab.models.Comment
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vynils-back-heroku.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: NetworkServiceAdapter(context).also {
                        instance = it
                    }
                }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getAlbums( onComplete:(resp:List<Album>)->Unit , onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                }))
    }
    fun getCollectors(  onComplete:(resp:List<Collector>)->Unit , onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("collectors",
                Response.Listener<String> { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(i, Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                }))
    }
    fun getComments( albumId:Int, onComplete:(resp:List<Comment>)->Unit , onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("albums/$albumId/comments",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Comment>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val rating = item.getInt("rating").toString()
                        val description = item.getString("description")
                        Log.d("Response", item.toString())
                        list.add(i, Comment(albumId = albumId, rating = rating, description = description))
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                }))
    }
    fun postComment(body: JSONObject, albumId: Int,  onComplete:(resp:JSONObject)->Unit , onError: (error:VolleyError)->Unit){
        requestQueue.add(postRequest("albums/$albumId/comments",
                body,
                Response.Listener<JSONObject> { response ->
                    onComplete(response)
                },
                Response.ErrorListener {
                    onError(it)
                }))
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}