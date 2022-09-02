package com.example.travelplan_finalfianl.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.adapters.CalendarListRecylerViewAdapter
import com.example.travelplan_finalfianl.addlist.EditCalendarListActivity
import com.example.travelplan_finalfianl.databinding.ActivityCalendarListFragmentBinding
import com.example.travelplan_finalfianl.models.BasicResponse
import com.example.travelplan_finalfianl.models.CalendarListData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class CalendarListFragment : BaseFragment() {

    lateinit var binding: ActivityCalendarListFragmentBinding
    lateinit var mCalendarAdaper: CalendarListRecylerViewAdapter
    var mCalendarList = ArrayList<CalendarListData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.activity_calendar_list_fragment,
            // R.layout.list_item_travel_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {


        binding.addTodayBtn.setOnClickListener {
            val myIntent = Intent(mContext, EditCalendarListActivity::class.java)
            startActivity(myIntent)
        }


    }

    override fun onResume() {
        super.onResume()
        getCalendarListFromServer()
    }

    override fun setValues() {

        mCalendarAdaper = CalendarListRecylerViewAdapter(mContext, mCalendarList, false)
        binding.calendarListsRecyclerView.adapter = mCalendarAdaper

        binding.calendarListsRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getCalendarListFromServer() {

        apiList.getRequestMdataList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mCalendarList.clear()
                    val sdf = SimpleDateFormat("h:mm:ss")
                    for (data in br.data.calendarlists) {
                        val time = sdf.format(data.datetime)
                        if (time == "6:11:11") {
                            mCalendarList.add(data)
                        }
                    }
                    mCalendarAdaper.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}