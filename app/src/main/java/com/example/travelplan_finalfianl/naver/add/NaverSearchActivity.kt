package com.example.travelplan_finalfianl.naver.add

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.naver.adapters.PlaceListRecylerViewAdapters
import com.example.travelplan_finalfianl.naver.data.BasicResponseNaver
import com.example.travelplan_finalfianl.databinding.ActivityEditTodoBinding
import com.example.travelplan_finalfianl.naver.ApIListNaver
import com.example.travelplan_finalfianl.naver.data.NaverApiData
import com.example.travelplan_finalfianl.naver.NaverServerApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NaverSearchActivity: AppCompatActivity() {

    lateinit var binding: ActivityEditTodoBinding
    lateinit var mPlaceAdapter: PlaceListRecylerViewAdapters
    var mPlaceList = ArrayList<NaverApiData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_todo)

        val retrofit = NaverServerApi.getRetrofit()
        val apiList1 = retrofit.create(ApIListNaver::class.java)



        mPlaceAdapter = PlaceListRecylerViewAdapters(this, mPlaceList)
        binding.recyclerView.adapter = mPlaceAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.placeSearchBtn.setOnClickListener {
            val inputKeyword = binding.placeNameEdt.text.toString()
            if (inputKeyword.length < 2) {
                Toast.makeText(this, "검색어는 두글자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiList1.getSearchlocal(inputKeyword, 5).enqueue(object : Callback<BasicResponseNaver>{
                override fun onResponse(call: Call<BasicResponseNaver>, response: Response<BasicResponseNaver>) {
                    Log.d("json", response.toString())

                    if (response.isSuccessful) {
                        Log.d("성공", response.body().toString())

                        mPlaceList.clear()
                        mPlaceList.addAll(response.body()!!.items)
                        mPlaceAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<BasicResponseNaver>, t: Throwable) {
                    Log.d("서버 실패", t.toString())
                }
            })
        }

    }
}