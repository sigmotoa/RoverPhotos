package com.sigmotoa.roverphotos.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

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

    @GET("rovers/{rover}?api_key=DEMO_KEY")
    suspend fun getRover(@Path(value = "rover") rover:String):Rover
}