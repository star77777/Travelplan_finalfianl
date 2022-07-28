package com.example.travelplan_finalfianl.addlist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.databinding.ActivityEditCalendarListBinding
import com.example.travelplan_finalfianl.models.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

public class EditCalendarListActivity : BaseActivity() {
    lateinit var binding: ActivityEditCalendarListBinding

    // private lateinit var rv: RecyclerView

    //    선택한 약속 일시를 저장할 멤버변수
    val mSelectedDateTime = Calendar.getInstance()!!  // 기본값 : 현재시간
    val mSelectedDateTime2= Calendar.getInstance()!!
    //  var mSelectedLatLng : LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_calendar_list)


        setupEvents()
        setValues()

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun setupEvents() {

//가는 날
        binding.firstDateTxt.setOnClickListener {
////            날짜를 선택하고 할 일(인터페이스)를 작성
            val dl = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    mSelectedDateTime.set(year, month, day)
                    val sdf = SimpleDateFormat("yyyy. M. d ")
                    Log.d("선택된 시간", sdf.format(mSelectedDateTime.time))
//
                    binding.firstDateTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }
//
//////            DatePickerDialog 팝업
            val dpd = DatePickerDialog(
                mContext,
                dl,
                mSelectedDateTime.get(Calendar.YEAR),
                mSelectedDateTime.get(Calendar.MONTH),
                mSelectedDateTime.get(Calendar.DAY_OF_MONTH)
            )
//
            dpd.show()
        }
////        //오늘날
////        //            날짜 선택
        //가는 날
        binding.lastDateTxt.setOnClickListener {
////            날짜를 선택하고 할 일(인터페이스)를 작성
            val da = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    mSelectedDateTime2.set(year, month, day)
                    val sda = SimpleDateFormat("yyyy. M. d")
                    Log.d("선택된 시간", sda.format(mSelectedDateTime2.time))
//
                    binding.lastDateTxt.text = sda.format(mSelectedDateTime2.time)
                }
            }
//
//////            DatePickerDialog 팝업
            val dpa = DatePickerDialog(
                mContext,
                da,
                mSelectedDateTime2.get(Calendar.YEAR),
                mSelectedDateTime2.get(Calendar.MONTH),
                mSelectedDateTime2.get(Calendar.DAY_OF_MONTH)
            )
//
            dpa.show()
        }

//}

        binding.addBtn.setOnClickListener {

            //            1. 약속의 제목 정했는가
//            1. 약속의 제목 정했는가
            val inputTitle = binding.titleEdt.text.toString()


//            2. 날짜/시간이 선택이 되었는가?
//             =>날짜 / 기간 중 선택 안한게 있다면, 선택하라고 토스트 함수를 강제 종료하자.
            if (binding.firstDateTxt.text == "가는 날") {
                Toast.makeText(mContext, "약속 일자를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.lastDateTxt.text == "오는 날") {
                Toast.makeText(mContext, "약속 일자를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // var calcuDate=(mSelectedDateTime.time.time-mSelectedDateTime2.time.time)

            // Log.d("test:날짜!","$calcuDate 일 차이남!!")

            // val challengeDay =(calcuDate+1).toInt()
            //binding.challengeCountdownDay.text ="Day $(challengeDay)"
//            서버에서 요구한 약속일시 양식대로 변환하여 전달
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val sda = SimpleDateFormat("yyyy. MM. dd")
            val latitude = 0.0
            val longitude = 0.0


            val inputPlaceName = binding.placeNameEdt.text.toString()

            apiList.postRequestdataList(
                inputTitle,
                sdf.format(mSelectedDateTime.time),
                //  sda.format(mSelectedDateTime2.time),
                inputPlaceName,
                latitude,
                longitude,
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
                    }
                    else {
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
//           titleTxt.text = "새 약속 만들기"
    }

//    abstract val mList: List<CalendarListData>
//    val adapter = CalendarListRecylerViewAdapter(mList) //adapter 생성
//
//    adapter!!.itemClick =object:CalendarListRecylerViewAdapter.ItemClick{
//        override fun onClick(view: View, position: Int) {
//            val intent = Intent(context,TodoListActivity::class.java)
//            startActivity(intent)
//        }
    // }
}
