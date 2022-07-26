package com.example.travelplan_finalfianl.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.databinding.ActivityLoginBinding
import com.example.travelplan_finalfianl.ui.signup.SignUpActivity

class LoginActivity : BaseActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.loginBtn.setOnClickListener {

        }
        binding.signUpBtn.setOnClickListener {
            val myIntent= Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {

    }
}