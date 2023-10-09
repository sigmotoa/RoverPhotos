package com.sigmotoa.roverphotos.data.remote

import retrofit2.http.GET

/**
 *
 * Created by sigmotoa on 8/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
interface RoversService {

    @GET("rovers?api_key=DEMO_KEY")
    suspend fun getRovers(): Rovers
}