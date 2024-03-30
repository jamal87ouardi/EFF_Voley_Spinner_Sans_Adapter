package com.example.m205_tp3_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var sa = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://mocki.io/v1/c815b063-5d3a-4c5b-ad6f-8f457c52d6e1"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                sa = response

                val moviesList = ArrayList<String>()



                for (i in 0 until sa.length()) {
                    val jsonObject: JSONObject = sa.getJSONObject(i)

                    val name = jsonObject.getString("name")
                    val price = jsonObject.getDouble("price")

                    val displayed = name+" - "+price.toString()+" MAD"


                    moviesList.add(displayed)

                }

                val sp= findViewById<Spinner>(R.id.spinner)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moviesList)
                sp.adapter = adapter
            },
            { error ->

            }
        )


        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)


    }
}