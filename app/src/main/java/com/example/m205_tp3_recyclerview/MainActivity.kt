package com.example.m205_tp3_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://run.mocky.io/v3/d9293f76-e064-4731-ba9f-dcef017cca1a"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // Gérez la réponse du tableau JSON
                val moviesList = mutableListOf<Movie>()



                for (i in 0 until response.length()) {
                    val jsonObject: JSONObject = response.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val price = jsonObject.getDouble("price")
                    val image = jsonObject.getString("image")

                    val movie = Movie(id, name, price, image)
                    moviesList.add(movie)
                }

                // Mettez à jour l'adaptateur de RecyclerView avec les données analysées
                val adapter = MovieAdapter()
                adapter.updateMovies(moviesList)
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = adapter

                recyclerView.layoutManager = LinearLayoutManager(this)
            },
            { error ->
                // Gérez les erreurs
            }
        )

// Ajoutez la requête à la RequestQueue
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)


    }
}