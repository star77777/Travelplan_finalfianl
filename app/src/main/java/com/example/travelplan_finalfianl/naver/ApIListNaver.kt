package com.example.travelplan_finalfianl.naver

import com.example.travelplan_finalfianl.naver.data.BasicResponseNaver
import com.example.travelplan_finalfianl.naver.data.GeoResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface ApIListNaver {

    @DELETE("user")
    fun deleteRequestUser(
        @Query("text") text : String
    ) : Call<BasicResponseNaver>

    @GET("local")
    fun getSearchlocal(
        @Query("query") keyword: String,
        @Query("display") display : Int
    ): Call<BasicResponseNaver>

    @GET("map-reversegeocode/v2/gc")
    fun getRequestMapAddress(
        @Query("coords") coords : String,
        @Query("output") output : String,
        @Query("orders") orders : String
    ) : Call<GeoResponse>
}