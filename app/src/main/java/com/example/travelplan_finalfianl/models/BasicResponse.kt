package com.example.travelplan_finalfianl.models

import java.io.Serializable


data class BasicResponse(
    val code : Int,
    val message : String,
    val data : DataResponse,
)  {
}