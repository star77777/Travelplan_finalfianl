package com.example.travelplan_finalfianl.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.databinding.ActivityLoginBinding

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

        }
    }

    override fun setValues() {

    }
}