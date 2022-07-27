package com.example.travelplan_finalfianl.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.MainActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.models.BasicResponse
import com.example.travelplan_finalfianl.ui.main.LoginActivity
import com.example.travelplan_finalfianl.utils.ContextUtil
import com.example.travelplan_finalfianl.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {
    var isTokenOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        apiList.getRequestMyInfo(ContextUtil.getLoginToken(mContext)).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!

                    isTokenOk = true
                    GlobalData.loginUser = br.data.user
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    override fun setValues() {
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({
            val myIntent :Intent

            Log.d("토큰", isTokenOk.toString())
            Log.d("CB", ContextUtil.getAutoLogin(mContext).toString())

            if (isTokenOk && ContextUtil.getAutoLogin(mContext)) {
                Toast.makeText(
                    mContext,
                    "${GlobalData.loginUser!!.nick_name}님 환영합니다.",
                    Toast.LENGTH_SHORT
                ).show()
                myIntent = Intent(mContext, MainActivity::class.java)
            }
            else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }
            startActivity(myIntent)
            finish()
        },2500)
    }
}