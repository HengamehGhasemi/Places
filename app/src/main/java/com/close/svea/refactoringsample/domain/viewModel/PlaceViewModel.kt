package com.close.svea.refactoringsample.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.network.repository.GetPlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor (
    private val repository: GetPlacesRepository
) : ViewModel() {

    var places: LiveData<List<PlaceEntity>> = repository.getAllPlaces()

    suspend fun fetchAllPlaces(){
        viewModelScope.let {
            repository.fetchAllPlaces()
        }
    }
}