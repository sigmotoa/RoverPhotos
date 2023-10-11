package com.sigmotoa.roverphotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.sigmotoa.roverphotos.ui.screens.home.Home

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

   // val db = Room.databaseBuilder(
     //   applicationContext,
       // PhotosDatabase::class.java, name = "photos-db"
    //).build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Home()
            //Home(db.photosDao())
        }

    }
}

//"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631300503690E01_DXXX.jpg"

//https://mars.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631300503690E01_DXXX.jpg