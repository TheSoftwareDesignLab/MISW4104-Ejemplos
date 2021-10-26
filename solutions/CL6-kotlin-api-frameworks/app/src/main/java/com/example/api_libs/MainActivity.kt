package com.example.api_libs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.api_libs.brokers.VolleyBroker
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var volleyBroker: VolleyBroker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyBroker = VolleyBroker(this.applicationContext)

        val getButton: Button = findViewById(R.id.fetch_button)
        val getResultTextView : TextView = findViewById(R.id.get_result_text)
        getButton.setOnClickListener {
            volleyBroker.instance.add(VolleyBroker.getRequest("collectors",
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    getResultTextView.text = "Response is: ${response.substring(0, 500)}"
                },
                Response.ErrorListener {
                    Log.d("TAG", it.toString())
                    getResultTextView.text = "That didn't work!"
                }
            ))
        }

        val postButton: Button = findViewById(R.id.post_button)
        val postResultTextView : TextView = findViewById(R.id.post_result_text)
        postButton.setOnClickListener {
            val mailTxt : TextInputEditText = findViewById(R.id.txt_post_mail)
            val nameTxt : TextInputEditText = findViewById(R.id.txt_post_name)
            val phoneTxt : TextInputEditText = findViewById(R.id.txt_post_phone)
            val postParams = mapOf<String, Any>(
                "name" to nameTxt.text.toString(),
                "telephone" to phoneTxt.text.toString(),
                "email" to mailTxt.text.toString()
            )
            volleyBroker.instance.add(VolleyBroker.postRequest("collectors", JSONObject(postParams),
                Response.Listener<JSONObject> { response ->
                    // Display the first 500 characters of the response string.
                    postResultTextView.text = "Response is: ${response.toString()}"
                },
                Response.ErrorListener {
                    Log.d("TAG", it.toString())
                    postResultTextView.text = "That didn't work!"
                }
            ))
        }

        val putButton: Button = findViewById(R.id.put_button)
        val putResultTextView : TextView = findViewById(R.id.put_result_text)
        putButton.setOnClickListener {
            val idTxt : TextInputEditText = findViewById(R.id.txt_put_id)
            val mailTxt : TextInputEditText = findViewById(R.id.txt_put_mail)
            val nameTxt : TextInputEditText = findViewById(R.id.txt_put_name)
            val phoneTxt : TextInputEditText = findViewById(R.id.txt_put_phone)
            val putParams = mapOf<String, Any>(
                "id" to idTxt.text.toString(),
                "name" to nameTxt.text.toString(),
                "telephone" to phoneTxt.text.toString(),
                "email" to mailTxt.text.toString()
            )
            volleyBroker.instance.add(VolleyBroker.putRequest("collectors", JSONObject(putParams),
                Response.Listener<JSONObject> { response ->
                    // Display the first 500 characters of the response string.
                    putResultTextView.text = "Response is: ${response.toString()}"
                },
                Response.ErrorListener {
                    Log.d("TAG", it.toString())
                    putResultTextView.text = "That didn't work!"
                }
            ))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        supportActionBar!!.title = "Volley"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, RetrofitActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

