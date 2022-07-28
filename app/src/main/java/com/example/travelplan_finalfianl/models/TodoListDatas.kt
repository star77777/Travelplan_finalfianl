package com.example.travelplan_finalfianl.models

import java.util.*

class TodoListDatas(
    val id : Int,
    val title : String,
    val datetime : Date,
    val place : String,
    val latitude : Double,
    val longitude : Double,
    val user : UserData,
){

}