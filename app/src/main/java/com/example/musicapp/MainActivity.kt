package com.example.musicapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent

import retrofit2.create
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //CALL THE RECYCLERVIEW FROM ACTIVITY_MAIN AND WE WILL SET THE DATA IN THIS WITH THE HELP OF MYADAPTER
        myRecyclerView = findViewById(R.id.recyclerView)

        //BUTTONS FOR SHOWING LIST OF LIKED SONGS
        val likedSongs = findViewById<Button>(R.id.saved)
        //ADD A FUNCTIONALITY FOR CHANGE PAGE FOR SHOWING A LIST OF LIKED SONGS

        likedSongs.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }




        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)

        val retrofitdata = retrofitBuilder.getData("eminem")

        //CONVERT THE JAVA CLASS TO JSON FORMATE
        retrofitdata.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList = response.body()?.data!!
//                findViewById<TextView>(R.id.helloText).text = dataList.toString()  --->  (FOR DISPLAY THE DIRECT API CONTENT)

                //CALL THE MYADAPTER BY PASSING DATALIST (THIS WILL RETURN YOU EACH_CARD)
                myAdapter = MyAdapter(this@MainActivity, dataList)

                //NOW SET THE LINK BETWEEN ADAPTER AND RECYCLERVIEW MEANS SET THE DATA IN RECYCLERVIEW
                myRecyclerView.adapter = myAdapter
                myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                Log.d("TAG: onResponse", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("TAG: onFailure", "onFailure" + t.message)
            }
        })
    }
}