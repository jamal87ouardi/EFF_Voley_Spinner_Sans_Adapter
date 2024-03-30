package com.example.m205_tp3_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

                val moviesList = mutableListOf<Movie>()



                for (i in 0 until sa.length()) {
                    val jsonObject: JSONObject = sa.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val price = jsonObject.getDouble("price")
                    val image = jsonObject.getString("image")

                    val movie = Movie(id, name, price, image)
                    moviesList.add(movie)

                }


                val adapter = MovieAdapter(moviesList)

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = adapter

                recyclerView.layoutManager = LinearLayoutManager(this)
            },
            { error ->

            }
        )


        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)


    }
}