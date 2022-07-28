package com.example.travelplan_finalfianl.models


data class BasicResponse(
    val code : Int,
    val message : String,
    val data : DataResponse,
) {
}