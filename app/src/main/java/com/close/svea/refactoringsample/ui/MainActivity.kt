package com.close.svea.refactoringsample.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.close.svea.refactoringsample.*
import com.close.svea.refactoringsample.domain.viewModel.PlaceViewModel
import com.close.svea.refactoringsample.domain.util.PlaceMapper
import com.close.svea.refactoringsample.network.models.Place
import com.close.svea.refactoringsample.util.checkTheInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mapper = PlaceMapper()//map placeEntity to placeModel vice verse
    private val placeViewModel by viewModels<PlaceViewModel>()

    //View Items
    private lateinit var placesRecyclerView: RecyclerView
    private lateinit var notConnectedMessage: TextView
    private lateinit var getPlacesButton: Button
    private lateinit var bottomRoundShapeView: View
    private lateinit var notConnectedImageView: ImageView
    private lateinit var mapImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //find views
        placesRecyclerView = findViewById(R.id.places_recycler_view)//to show list of places
        notConnectedMessage = findViewById(R.id.not_connected_message_text_view)//message for use if the app have problem to connect to internet
        getPlacesButton = findViewById(R.id.get_places_button)//button for press to get places
        bottomRoundShapeView = findViewById(R.id.bottom_round_shape_view)
        notConnectedImageView = findViewById(R.id.not_connected_img)
        mapImage = findViewById(R.id.map_image_view)

        placesRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        getPlacesButton.setOnClickListener(this)

        placeViewModel.places.observe(this){
            if (it.isNotEmpty())
                sendDataForAdapter(mapper.toPlaceList(it))
                //whenever the places come the recycler will shows them
        }
    }

    override fun onClick(v: View?) {

        getPlacesButton.text = getString(R.string.show_places)

        if (checkTheInternetConnection(applicationContext)) {
            fetchPlaceList()
            showPlaceList()
        } else { // if the connection has problem or not available
          showTheInternetConnectionProblemPage()
        }
    }

    private fun sendDataForAdapter(placeList : List<Place>){
        placesRecyclerView.adapter = PlacesAdapter(placeList)//map places entity to place object

    }

    private fun fetchPlaceList(){
        CoroutineScope(Main).launch {
            placeViewModel.fetchAllPlaces() //get all places from Api
        }
    }

    private fun showPlaceList(){

        placesRecyclerView.visibility = View.VISIBLE
        notConnectedMessage.visibility = View.GONE
        getPlacesButton.visibility = View.GONE
        bottomRoundShapeView.visibility = View.GONE
        notConnectedImageView.visibility = View.GONE
        mapImage.visibility = View.GONE
    }

    private fun showTheInternetConnectionProblemPage(){

        notConnectedMessage.visibility = View.VISIBLE
        notConnectedImageView.visibility = View.VISIBLE
        mapImage.visibility = View.GONE
        getPlacesButton.text = getString(R.string.try_again)
    }
}

