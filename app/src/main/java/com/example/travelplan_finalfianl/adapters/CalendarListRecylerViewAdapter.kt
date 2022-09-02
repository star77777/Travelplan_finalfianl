package com.example.travelplan_finalfianl.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplan_finalfianl.databinding.ListItemTravelListBinding
import com.example.travelplan_finalfianl.fragments.TodoListActivityy
import com.example.travelplan_finalfianl.models.CalendarListData
import java.text.SimpleDateFormat

 class CalendarListRecylerViewAdapter(
    val mContext: Context,
    val mList: List<CalendarListData>,
    val isInvited: Boolean,

    // val itemClick: (CalendarListData) -> Unit

) : RecyclerView.Adapter<CalendarListRecylerViewAdapter.ItemViewHolder>() {
    interface ItemClick { //인터페이스
        fun onClick(view: View, position: Int)
   }


    inner class ItemViewHolder(var binding: ListItemTravelListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CalendarListData) {

      //      binding.titleTxt.text = item.title

            val sdf = SimpleDateFormat("M/d ")

            binding.dateTxt.text = "${sdf.format(item.datetime)}"


            binding.titleTxt.text = "제목:${item.title}"

            binding.placeTxt.text = "약속 장소 : ${item.place}"

//            리싸이클러뷰 한칸의 레이아웃 클릭 이벤트
            binding.TodayEdt.setOnClickListener{
                val myIntent = Intent(mContext, TodoListActivityy::class.java)
                myIntent.putExtra("travelData", item)
                mContext.startActivity(myIntent)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemTravelListBinding
                .inflate(LayoutInflater.from(mContext), parent, false)

        )

    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

