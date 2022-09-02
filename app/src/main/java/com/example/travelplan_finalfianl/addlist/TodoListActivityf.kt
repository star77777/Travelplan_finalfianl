package com.example.travelplan_finalfianl.addlist

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.naver.adapters.PlaceListRecylerViewAdapters
import com.example.travelplan_finalfianl.adapters.TodoListRecyclerViewAdapers
import com.example.travelplan_finalfianl.naver.data.BasicResponseNaver
import com.example.travelplan_finalfianl.databinding.ActivityEditTodoBinding
import com.example.travelplan_finalfianl.models.BasicResponse
import com.example.travelplan_finalfianl.models.CalendarListData
import com.example.travelplan_finalfianl.naver.ApIListNaver
import com.example.travelplan_finalfianl.naver.data.NaverApiData
import com.example.travelplan_finalfianl.naver.NaverServerApi
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class TodoListActivityf : BaseActivity() {
    lateinit var binding: ActivityEditTodoBinding
    lateinit var mAdapter: TodoListRecyclerViewAdapers

    lateinit var travelData: CalendarListData
    lateinit var AppointData: NaverApiData
    var mPlaceList = ArrayList<NaverApiData>()
    var mPlaceListMap = ArrayList<NaverApiData>()

    //    선택한 약속 일시를 저장할 멤버변수
    val mSelectedDateTime2 = Calendar.getInstance()!!// 기본값 : 현재시간

    //   장소를 담고있는 관련 변수
    var mStartPlaceList = ArrayList<NaverApiData>()
    lateinit var mPlaceAdapter: PlaceListRecylerViewAdapters
    lateinit var mSelectedPlace: NaverApiData

    lateinit var NaverApiDatas: NaverApiData

    //    네이버 지도 관련 변수
    var mNaverMap: NaverMap? = null
    var mStartPlaceMarker = Marker()  // 출발지에 표시될 하나의 마커
    var mSelectedPlaceMarker = Marker()  // 도착지에 표시될 하나의 마커
    var mSelectedLatLng: LatLng? = null
    lateinit var mExponseData: CalendarListData
    lateinit var naverRetrofit: Retrofit

    lateinit var apiList1: ApIListNaver
    // lateinit var apiList2:ApIListNaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_todo)

        travelData = intent.getSerializableExtra("travelData") as CalendarListData

        naverRetrofit = NaverServerApi.getRetrofit()
        apiList1 = naverRetrofit.create(ApIListNaver::class.java)

        // AppointData = intent.getSerializableExtra("AppointData") as NaverApiData


        setupEvents()
        setValues()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupEvents() {
        //시간 선택
        binding.timeTxt.setOnClickListener {

            val tsl = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                    mSelectedDateTime2.set(Calendar.HOUR_OF_DAY, hour)
                    mSelectedDateTime2.set(Calendar.MINUTE, minute)

                    //   오후 12:10 형태로 가공
                    val sdf = SimpleDateFormat("a h:mm")
                    binding.timeTxt.text = sdf.format(mSelectedDateTime2.time)

                }
            }

            TimePickerDialog(
                mContext,
                tsl,
                mSelectedDateTime2.get(Calendar.HOUR_OF_DAY),
                mSelectedDateTime2.get(Calendar.MINUTE),
                false
            ).show()


        }
        binding.placeSearchBtn.setOnClickListener {


            val inputKeyword = binding.placeNameEdt.text.toString()

            mPlaceAdapter = PlaceListRecylerViewAdapters(this, mPlaceList)
            binding.recyclerView.adapter = mPlaceAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            if (inputKeyword.length < 2) {
                Toast.makeText(this, "검색어는 두글자 이상 작성해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiList1.getSearchlocal(inputKeyword, 5).enqueue(object : Callback<BasicResponseNaver> {
                override fun onResponse(
                    call: Call<BasicResponseNaver>,
                    response: Response<BasicResponseNaver>
                ) {
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

        binding.addBtn2.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
            //    val inputPlaceName = CalendarListData.inputTitle
            //binding.placeNameEdt.text.toString()

            if (binding.timeTxt.text == "시간 선택") {
                Toast.makeText(mContext, "약속 시간을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //var exponseTxt= binding.exponseEdt.Int()
            val placeNameEdt = binding.placeNameEdt.text.toString()
            if (placeNameEdt.isBlank()) {
                Toast.makeText(mContext, "약속 장소명을 기입해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.placeTxt.visibility != View.VISIBLE) {
                Toast.makeText(mContext, "일정 장소를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var inputPlaceName = binding.placeTxt.text.toString()

            var inputTitle = binding.textTxt.text.toString()
            // val inputPlaceName =binding.AppointData.
            apiList.postRequestdataList(
                inputTitle,
                sdf.format(mSelectedDateTime2.time),
                //  sda.format(mSelectedDateTime2.time),
                //  mSelectedPlace.title,
                inputPlaceName,
                mSelectedLatLng!!.latitude,
                mSelectedLatLng!!.longitude,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {


                    if (response.isSuccessful) {
                        Log.d("", response.body()!!.toString())
                        Toast.makeText(mContext, "일정이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                        Log.d("현재올린 일정정보", response.body()!!.data.calendarlist.toString())
                        finish()
                    } else {
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        val message = jsonObj.getString("message")
                        Log.d("서버 문제", message)
                    }


                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

        }

    }

    override fun setValues() {
        binding.textTxt.text = travelData.title
    }
}
