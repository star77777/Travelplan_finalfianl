package com.example.travelplan_finalfianl

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplan_finalfianl.api.APIList
import com.example.travelplan_finalfianl.api.ServerApi
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {


    lateinit var mContext: Context

    //  lateinit var titleTxt : TextView
    lateinit var retrofit: Retrofit
    lateinit var apiList: APIList
    lateinit var addBtn: ImageView
    // mSelectedDateTime = Calendar.getInstance ()  // Calendar () 생성자 사용 X
    //val mSelectedDateTime2 = Calendar.getInstance ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        retrofit = ServerApi.getRetrofit(mContext)
        apiList = retrofit.create(APIList::class.java)
    }

    abstract fun setupEvents()

    abstract fun setValues()
}