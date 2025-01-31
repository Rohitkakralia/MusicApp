package com.example.musicapp

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.content.Intent


class MyAdapter(val context: Activity, val dataList: List<Data>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val title: TextView
        val play: ImageButton
        val pause: ImageButton
        val cardView: View

        init{
            image = itemView.findViewById(R.id.musicImage)
            title = itemView.findViewById(R.id.musicTitle)
            play = itemView.findViewById(R.id.btnPlay)
            pause = itemView.findViewById(R.id.btnPause)
            cardView = itemView.findViewById(R.id.cardView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //It create the data incase the layout manager fail to create view of data
        val itemView = LayoutInflater.from(context).inflate(R.layout.each_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //populate the data into view
        val currentData = dataList[position]
        val mediaPlayer = MediaPlayer.create(context, currentData.preview.toUri())

        holder.title.text = currentData.title
        Picasso.get().load(currentData.album.cover).into(holder.image);
        holder.play.setOnClickListener{
            mediaPlayer.start()
        }

        holder.pause.setOnClickListener{
            mediaPlayer.stop()
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, SecondActivity::class.java).apply {
                putExtra("MUSIC_TITLE", currentData.title)
                putExtra("MUSIC_COVER", currentData.album.cover)
                putExtra("MUSIC_PREVIEW", currentData.preview)
            }
            context.startActivity(intent)
        }

    }

}