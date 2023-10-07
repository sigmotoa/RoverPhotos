package com.sigmotoa.roverphotos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * Created by sigmotoa on 6/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
class MainViewModel : ViewModel() {

    //var state by mutableStateOf(UiState())

    //LiveData

    //StateFlow
    private val _state = MutableStateFlow(UiState())


    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = false,
                photos = Retrofit.Builder()
                    .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PhotosService::class.java)
                    .getPhotos()
                    .photos
            )
        }
    }

    //val state: LiveData<UiState> = _state
//private val _state = MutableLiveData(UiState())
//  private set
    fun onPhotoClick(photo: Photo) {

        val photos = _state.value.photos.toMutableList()
        photos.replaceAll { if (it.id == photo.id) photo.copy(like = !photo.like) else it }

        _state.value = _state.value.copy(photos=photos)

    }

    data class UiState(
        val loading: Boolean = false,
        val photos: List<Photo> = emptyList()

    )
}
