package com.example.m205_tp3_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

data class Movie(val id:Int, val name:String, val price:Double, val image:String)
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

                val list1 = ArrayList<String>()

                val list2 = ArrayList<Movie>()



                for (i in 0 until sa.length()) {
                    val jsonObject: JSONObject = sa.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val price = jsonObject.getDouble("price")
                    val image = jsonObject.getString("image")

                    val displayed = name+" - "+price.toString()+" MAD"

                    list1.add(displayed)

                    val movie = Movie(id,name,price,image)

                    list2.add(movie)

                }

                val sp= findViewById<Spinner>(R.id.spinner)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list1)
                sp.adapter = adapter

                sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {

                        val selectedItem = list2[position]

                        findViewById<TextView>(R.id.tvName).text = selectedItem.name
                        findViewById<TextView>(R.id.tvPrice).text = selectedItem.price.toString()+" MAD"
                        Picasso.get().load(selectedItem.image).into(findViewById<ImageView>(R.id.imageView))

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Handle case when no item is selected
                    }
                }




            },
            { _ ->

            }
        )


        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)


    }
}