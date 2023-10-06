package com.sigmotoa.roverphotos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sigmotoa.roverphotos.ui.theme.RoverPhotosTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoverPhotosTheme {

                val photosNasa = produceState<List<Photo>>(initialValue = emptyList()) {

                    value = Retrofit.Builder()
                        .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PhotosService::class.java)
                        .getPhotos()
                        .photos
                    //.forEach{ it -> Log.d("Outs", it.toString())}
                }
                //val photos =
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = { TopAppBar(title = { Text(text = "Photos Mars") }) }
                    ) { padding ->

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            modifier = Modifier.padding(padding),
                            contentPadding = PaddingValues(4.dp)
                        ) {

                            items(photosNasa.value) {
                                Column {
                                    AsyncImage(
                                        model = it.img_src.replace(".jpl", "")
                                            .replace("http", "https"),
                                        contentDescription = it.earth_date,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(2 / 3f)


                                    )
                                    Text(
                                        text = it.id.toString(),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = it.earth_date)
                                }
                            }
                        }


                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoverPhotosTheme {
        Greeting("Android")
    }
}


//"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631300503690E01_DXXX.jpg"

//https://mars.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631300503690E01_DXXX.jpg