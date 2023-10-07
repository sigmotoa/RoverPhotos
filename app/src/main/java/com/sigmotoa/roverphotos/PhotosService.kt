package com.sigmotoa.roverphotos

import retrofit2.http.GET


/**
 *
 * Created by sigmotoa on 4/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
interface PhotosService {
//https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity
    @GET("photos?sol=50&camera=mast&api_key=DEMO_KEY")
    suspend fun getPhotos(): PhotoResult
}