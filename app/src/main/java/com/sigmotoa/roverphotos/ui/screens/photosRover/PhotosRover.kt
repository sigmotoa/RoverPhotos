package com.sigmotoa.roverphotos.ui.screens.photosRover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sigmotoa.roverphotos.data.local.PhotosDao
import com.sigmotoa.roverphotos.data.remote.Photo
import com.sigmotoa.roverphotos.ui.theme.RoverPhotosTheme

/**
 *
 * Created by sigmotoa on 8/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosRover(photosDao: PhotosDao) {
    RoverPhotosTheme {
//                val photosNasa = produceState<List<Photo>>(initialValue = emptyList()) {
//
//                    value = Retrofit.Builder()
//                        .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build()
//                        .create(PhotosService::class.java)
//                        .getPhotos()
//                        .photos
//                    //.forEach{ it -> Log.d("Outs", it.toString())}
//                }
//                //val photos =
        // A surface container using the 'background' color from the theme


        //LiveData
        //val viewModel:MainViewModel = viewModel()
        //val state by viewModel.state.observeAsState(MainViewModel.UiState())

        //StateFlow
        val viewModel: PhotoRoverViewModel = viewModel{PhotoRoverViewModel(photosDao)}
        val state by viewModel.state.collectAsState()



        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = "Photos Mars") }) }
            ) { padding ->
                if (state.loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (state.photos.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(180.dp),
                        modifier = Modifier.padding(padding),
                        contentPadding = PaddingValues(4.dp)
                    ) {

                        items(state.photos) { photo ->
                            PhotoItem(photo = photo) { viewModel.onPhotoClick(photo) }

                        }
                    }

                }
            }
        }
    }
}


@Composable
fun PhotoItem(photo: Photo, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Box {

            AsyncImage(
                model = photo.img_src.replace(".jpl", "")
                    .replace("http", "https"),
                contentDescription = photo.earth_date,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .padding(start = 4.dp, end = 4.dp)
            )
            if (photo.like) {
                Icon(
                    imageVector = Icons.TwoTone.Star,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .align(
                            Alignment.BottomStart
                        )
                        .scale(2f)
                        .padding(8.dp),
                    tint = Color.Yellow
                )
            }
        }
        Text(
            text = photo.id.toString(),
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = photo.earth_date, modifier = Modifier.padding(12.dp))
    }
}

