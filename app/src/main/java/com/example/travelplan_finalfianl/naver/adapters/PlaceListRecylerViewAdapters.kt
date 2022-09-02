package com.example.travelplan_finalfianl.naver.adapters

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplan_finalfianl.addlist.TodoListActivityf
import com.example.travelplan_finalfianl.databinding.ListItemPlaceBinding
import com.example.travelplan_finalfianl.naver.data.NaverApiData
import com.naver.maps.geometry.Tm128

class PlaceListRecylerViewAdapters (
    val mContext : Context,
    val mList : List<NaverApiData>
) : RecyclerView.Adapter<PlaceListRecylerViewAdapters.ItemViewHolder>() {
    inner class ItemViewHolder(var binding : ListItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NaverApiData) {

            binding.titleTxt.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(item.title).toString()
            }

            binding.addressTxt.text = item.roadAddress

//            추후 구현 예정 (맵뷰)
//            binding.showMapBtn.setOnClickListener {
//                val myIntent = Intent(mContext, MapActivity::class.java)
//                myIntent.putExtra("NaverApiData", item)
//                mContext.startActivity(myIntent)
//            }

            binding.placeListClick.setOnClickListener {
                (mContext as TodoListActivityf).binding.placeTxt.visibility = View.VISIBLE
                mContext.binding.placeTxt.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
                } else {
                    Html.fromHtml(item.title).toString()
                }
                val tm128 = Tm128(item.mapx.toDouble(), item.mapy.toDouble())
                mContext.mSelectedLatLng = tm128.toLatLng()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemPlaceBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}