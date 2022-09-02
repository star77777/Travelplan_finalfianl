package com.example.travelplan_finalfianl.naver.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.naver.data.GeoResponse
import com.example.travelplan_finalfianl.naver.data.ResultDatas
import com.example.travelplan_finalfianl.databinding.ActivityMapBinding
import com.example.travelplan_finalfianl.naver.ApIListNaver
import com.example.travelplan_finalfianl.naver.data.NaverApiData
import com.example.travelplan_finalfianl.naver.NaverMapServerAPI
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MapActivity: AppCompatActivity(){

    lateinit var binding:ActivityMapBinding

    lateinit var mapApiService:ApIListNaver
    lateinit var mPlaceData:NaverApiData

    var mSelectedLatitude = 37.5779235853308
    var mSelectedLongitude = 127.033553463647
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        lateinit var retrofit :Retrofit
        retrofit = NaverMapServerAPI.getRetrofit()
        mapApiService = retrofit.create(ApIListNaver::class.java)

        mPlaceData = intent.getSerializableExtra("placeData") as NaverApiData

        binding.addressTxt.text = mPlaceData.roadAddress

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync {
            val naverMap = it

            val tm128 = Tm128(mPlaceData.mapx.toDouble(), mPlaceData.mapy.toDouble())

            val coord = tm128.toLatLng()

            val cameraUpdate = CameraUpdate.scrollTo( coord )
            naverMap.moveCamera(cameraUpdate)

            val marker = Marker()
            marker.position = coord
            marker.map = naverMap

            naverMap.setOnMapLongClickListener { pointF, latLng ->
                marker.position = latLng
                marker.map = naverMap

                mSelectedLatitude = latLng.latitude
                mSelectedLongitude = latLng.longitude

                mapApiService.getRequestMapAddress(
                    "${latLng.longitude}, ${latLng.latitude}",
                    "json",
                    "legalcode,admcode,addr,roadaddr",
                ).enqueue(object : Callback<GeoResponse> {
                    override fun onResponse(
                        call: Call<GeoResponse>,
                        response: Response<GeoResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("주소목록", response.body()!!.results.toString())

                            var roadaddr : ResultDatas? = null

                            val results = response.body()!!.results

                            for (result in results) {
                                if (result.name == "roadaddr") {
                                    roadaddr = result
                                }
                            }
                            val address = if (roadaddr!!.land.number2 == "") {
                                "${roadaddr!!.region.area1.name} ${roadaddr!!.region.area2.name} ${roadaddr!!.land.name} ${roadaddr.land.number1}"
                            } else {
                                "${roadaddr!!.region.area1.name} ${roadaddr!!.region.area2.name} ${roadaddr!!.land.name} ${roadaddr.land.number1}-${roadaddr.land.number2}"
                            }

                            binding.addressTxt.text = address
                        }
                    }

                    override fun onFailure(call: Call<GeoResponse>, t: Throwable) {

                    }
                })
            }

        }
    }

}
