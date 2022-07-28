package com.example.travelplan_finalfianl.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class CalendarListData (
    val id : Int,
    val title : String,
    val datetime : Date,
    val place : String,
    val latitude : Double,
    val longitude : Double,
    val user : UserData,
){
}