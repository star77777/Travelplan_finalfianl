package com.example.travelplan_finalfianl.addlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.travelplan_finalfianl.BaseActivity
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.databinding.ActivityTodoListBinding

class TodoListActivity :  BaseActivity() {
    lateinit var binding : ActivityTodoListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_todo_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {


    }

    override fun setValues() {

    }
}