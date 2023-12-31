@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package com.sigmotoa.roverphotos.ui.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sigmotoa.roverphotos.data.remote.Rover
import com.sigmotoa.roverphotos.ui.theme.RoverPhotosTheme

/**
 *
 * Created by sigmotoa on 8/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */


@Composable
fun Home() {
    RoverPhotosTheme {
        val viewModel: HomeViewModel = viewModel()
        val state by viewModel.state.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = "Rovers List") }) },
                bottomBar = {
                    BottomAppBar(modifier = Modifier.fillMaxWidth(),
                        actions = {
                            IconButton(
                                modifier = Modifier.weight(1f),
//                                colors = IconButtonDefaults.iconButtonColors(
//                                    containerColor = MaterialTheme.colorScheme.surface,
//                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
//                                ),
                                onClick = {}) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(text = "Rovers")
                                    Icon(
                                        imageVector = Icons.TwoTone.Home,
                                        contentDescription = "Home",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            IconButton(modifier = Modifier.weight(1f),
                                onClick = {}) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Liked")
                                    Icon(
                                        imageVector = Icons.TwoTone.Star,
                                        contentDescription = "Favorite",
                                        tint = MaterialTheme.colorScheme.primary
                                    )

                                }
                            }
                            IconButton(
                                modifier = Modifier.weight(1f), onClick = { }) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "About")
                                    Icon(
                                        imageVector = Icons.TwoTone.Info,
                                        contentDescription = "About",
                                        tint = MaterialTheme.colorScheme.primary
                                    )

                                }
                            }
                        }
                    )

                }
            ) { padding ->
                run {
                    LazyColumn(modifier = Modifier.padding(padding)) {
                        items(state.rovers)
                        { rover ->
                            RoverItem(rover = rover, { viewModel.onRoverClick(rover) })
                        }
                    }
                }
            }

        }


    }

}

@Composable
fun RoverItem(rover: Rover, onClick: ()-> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .clickable ( onClick = onClick )
    ) {
        ConstraintLayout(modifier = Modifier.padding(start = 8.dp, end = 4.dp, bottom = 8.dp)) {
            val (photo, name, launched, landing, status, photos, maxDate, numCameras, data) = createRefs()

            Text(
                text = rover.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

            Log.d("rover", rover.name.capitalize())
            Box(
                modifier = Modifier
                    .size(520.dp, 520.dp)
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
                    .constrainAs(photo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
            ) {

                AsyncImage(
                    model = "https://raw.githubusercontent.com/corincerami/mars-photo-api/master/public/explore/images/${rover.name.capitalize()}_rover.jpg",
                    contentDescription = rover.name.capitalize(),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(data) {
                        top.linkTo(photo.bottom, margin = 4.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                        end.linkTo(parent.end, margin = 20.dp)
                        top.linkTo(photo.bottom, margin = 8.dp)
                    }) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Launch Date")
                    Text(text = "Landing Date")
                    Text(text = "Status")
                    Text(text = "Total Photos")
                    Text(text = "Max Date")
                    Text(text = "Total Sol")
                    Text(text = "Cameras")

                }
                Column(modifier = Modifier.weight(2f)) {
                    Text(text = rover.launch_date)
                    Text(text = rover.landing_date)
                    Text(text = rover.status)
                    Text(text = rover.total_photos.toString())
                    Text(text = rover.max_date)
                    Text(text = rover.max_sol.toString())
                    rover.cameras.forEach { Text(text = it.full_name, maxLines = 1) }.toString()

                }
            }

        }
    }

}

//@Preview
//@Composable
//fun RoverItemPrev() {
  //  RoverItem(rover = viewModel())
//}