package com.close.svea.refactoringsample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.close.svea.refactoringsample.util.ImageUrl
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.domain.util.imageLoader
import com.close.svea.refactoringsample.network.models.Place
import android.content.Intent
import android.net.Uri

class PlacesAdapter(private var places: List<Place>)
    :RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {


    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name_text_view)
        val description: TextView = itemView.findViewById(R.id.description_text_view)
        val image: ImageView = itemView.findViewById(R.id.place_image_view)
        val routMe: TextView = itemView.findViewById(R.id.rout_me_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = places[position].name
        holder.description.text = places[position].description
        holder.routMe.setOnClickListener {
            //create map intent to intent the user to google map
            intentToGoogleMap(position,it)
        }
        //using Glide library to load place images
        imageLoader(holder.image.context,ImageUrl+places[position].icon,holder.image)
    }

    override fun getItemCount(): Int = places.size

    //create map intent to intent the user to google map
    fun intentToGoogleMap(position: Int,view : View){
        val uri= "http://maps.google.com/maps?daddr=${places[position].latitude}," +
                "${places[position].longitude}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        view.context.startActivity(intent)
    }
}
