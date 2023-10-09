package com.sigmotoa.roverphotos.data.local

/**
 *
 * Created by sigmotoa on 8/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
data class Photo(
    val id: Int,
    val roverName: String,
    val cameraName: String,
    val marsDate: Int,
    val earthDate: String,
    val imgSrc: String,
    val like: Boolean
)