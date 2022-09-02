package com.example.travelplan_finalfianl.api


import com.example.travelplan_finalfianl.models.BasicResponse
import okhttp3.MultipartBody

import retrofit2.Call
import retrofit2.http.*

interface APIList {

    @FormUrlEncoded
    @PATCH("/user/password")
    fun patchPasswordChange(
        @Field("new_password") new_password: String,
        @Field("current_password") current_password: String,


        ): Call<BasicResponse>

    @GET("/appointment")
    fun getRequestMdataList(): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/appointment")
    fun postRequestdataList(
        @Field("title") title: String,
        @Field("datetime") datetime: String,
        @Field("place") place: String,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double,
    ): Call<BasicResponse>

    @GET("/user")
    fun getRequestMyInfo(): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserInfo(
        @Field("field") field: String,
        @Field("value") value: String,
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nickname: String,
    ): Call<BasicResponse>

    @GET("/user/check")
    fun getRequestUserCheck(
        @Query("type") type: String,
        @Query("value") value: String,
    ): Call<BasicResponse>

    @Multipart
    @PUT("/user/image")
    fun putRequestUserImage(@Part profileImg: MultipartBody.Part): Call<BasicResponse>


}