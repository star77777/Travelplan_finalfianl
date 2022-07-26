package com.example.travelplan_finalfianl.api


import com.example.travelplan_finalfianl.models.BasicResponse
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface APIList {
    @GET("/user/check")
    fun getRequestUserCheck (
        @Query("type") type : String,
        @Query("value") value : String,
    ) : Call<BasicResponse>
}