package com.example.travelplan_finalfianl.models

import java.io.Serializable
import java.util.*

data class TodoListDatas(
    val id : Int,
    val title : String,
    val datetime : Date,
    val place : String,
    val latitude : Double,
    val longitude : Double,
    val user : UserData,
)  {

}