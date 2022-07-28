package com.example.travelplan_finalfianl.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplan_finalfianl.databinding.ListItemTodoListsBinding
import com.example.travelplan_finalfianl.models.TodoListDatas
import java.text.SimpleDateFormat

class TodoListRecyclerViewAdapers(
    val mContext : Context,
    val mLists : List<TodoListDatas>,
    val isInvited : Boolean
): RecyclerView.Adapter<TodoListRecyclerViewAdapers.ItemHourViewHolder>() {
    inner class ItemHourViewHolder(val binding: ListItemTodoListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : TodoListDatas) {
            binding.titleHourTxt.text = item.title

            val sdfs = SimpleDateFormat("M/d a h:mm")

            binding.timeHourTxt.text = "${sdfs.format(item.datetime)}"
            binding.placeHourNameTxt.text = "약속 장소 : ${item.place}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHourViewHolder {
        return ItemHourViewHolder(
            ListItemTodoListsBinding
                .inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHourViewHolder, position: Int) {
        holder.bind(mLists[position])
    }

    override fun getItemCount(): Int {
        return mLists.size
    }
}