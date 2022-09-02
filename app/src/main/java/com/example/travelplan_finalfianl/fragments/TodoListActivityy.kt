package com.example.travelplan_finalfianl.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.adapters.TodoListRecyclerViewAdapers
import com.example.travelplan_finalfianl.addlist.TodoListActivityf
import com.example.travelplan_finalfianl.databinding.ActivityEditTodoListBinding
import com.example.travelplan_finalfianl.models.BasicResponse
import com.example.travelplan_finalfianl.models.CalendarListData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoListActivityy : BaseActivity() {

    lateinit var binding: ActivityEditTodoListBinding

    lateinit var mTodoListAdapter: TodoListRecyclerViewAdapers
    var mTodoList = ArrayList<CalendarListData>()


    lateinit var travelData: CalendarListData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_todo_list)
        travelData = intent.getSerializableExtra("travelData") as CalendarListData
        setupEvents()
        setValues()
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun setupEvents() {
        binding.addHourListBtn.setOnClickListener {
            val myIntent = Intent(mContext, TodoListActivityf::class.java)
            myIntent.putExtra("travelData", travelData)
            startActivity(myIntent)
        }


    }

    override fun onResume() {
        super.onResume()
        getMyAppointmentListFromServer()
    }


    override fun setValues() {


        mTodoListAdapter = TodoListRecyclerViewAdapers(mContext, mTodoList)
        binding.hourListRecyclerView.adapter = mTodoListAdapter
        binding.hourListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    //var lists= travelData.data.title
    fun getMyAppointmentListFromServer() {
        apiList.getRequestMdataList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    //mTodoList.clear()
                    mTodoList.clear()
                    val sdf = SimpleDateFormat("h:mm:ss")
                    for (data in br.data.calendarlists) {
////                        //  val time = sdf.format(data.datetime)
                        val title = data.title
////                        val hour = data.datetime
                        val title1 = travelData.title
////                        val hour1 = travelData.datetime
                        val time = sdf.format(data.datetime)
                        if (title == title1) {
                            if (time != "6:11:11") {
                                mTodoList.add(data)
                                Log.d("data", data.toString())
                                Log.d("list", mTodoList.size.toString())
                            }
                        }
                    }

                    // mTodoList.addAll(br.data.calendarlists)

                    mTodoListAdapter.notifyDataSetChanged()
                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }


        })

    }
}