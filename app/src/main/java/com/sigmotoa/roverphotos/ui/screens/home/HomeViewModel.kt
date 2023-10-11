package com.sigmotoa.roverphotos.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigmotoa.roverphotos.data.remote.Rover
import com.sigmotoa.roverphotos.data.remote.RoversService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * Created by sigmotoa on 8/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(
                rovers = Retrofit.Builder()
                    .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RoversService::class.java)
                    .getRovers()
                    .rovers
            )

        }
    }

    fun onRoverClick(rover: Rover) {
        viewModelScope.launch {
            _state.value = _state.value.copy(selected = rover.name)
            Log.d("HomeViewModel", "onRoverClick: ${rover.name}")
            _state.value= UiState(rover = Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoversService::class.java)
                .getRover(rover.name))
        }
    }

    data class UiState(
        val rover: Rover? = null,
        val selected: String = "",
        val rovers: List<Rover> = emptyList(),

        )
}
