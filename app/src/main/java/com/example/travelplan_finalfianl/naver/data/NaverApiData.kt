package com.example.travelplan_finalfianl.naver.data

import java.io.Serializable

data class NaverApiData(
    val title: String,
    val roadAddress: String,
    val mapx : Int,
    val mapy : Int
): Serializable {
}