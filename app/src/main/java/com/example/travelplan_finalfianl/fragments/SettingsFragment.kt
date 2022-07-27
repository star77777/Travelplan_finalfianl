package com.example.travelplan_finalfianl.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.travelplan_finalfianl.R
import com.example.travelplan_finalfianl.databinding.ActivitySettingsFragmentBinding
import com.example.travelplan_finalfianl.dialog.CustomAlertDialog
import com.example.travelplan_finalfianl.ui.main.LoginActivity
import com.example.travelplan_finalfianl.utils.ContextUtil
import com.example.travelplan_finalfianl.utils.GlobalData

class SettingsFragment : BaseFragment() {

    lateinit var binding : ActivitySettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_settings_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        프로필 이미지 변경 이벤트
        binding.profileImg.setOnClickListener {

        }

//        닉네임 변경 이벤트
        binding.changeNickLayout.setOnClickListener {

        }


//        비밀번호 변경 이벤트
        binding.changePwLayout.setOnClickListener {

        }


//        공유하기 이벤트
        binding.share.setOnClickListener {

        }

//        로그아웃
        binding.logoutLayout.setOnClickListener {
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "로그아웃"
            alert.binding.bodyTxt.text = "정말 로그아웃 하시겠습니까?"
            alert.binding.contentEdt.visibility = View.GONE
            alert.binding.positiveBtn.setOnClickListener {
//                로그인 토큰 (from ContextUtil)만 제거하고 싶을때 (기본값으로 set하자)
//                ContextUtil.setLoginToken(mContext, "")

                ContextUtil.clear(mContext)

                GlobalData.loginUser = null

                val myIntent = Intent(mContext, LoginActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)

                alert.dialog.dismiss()
            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }
        }
    }

    override fun setValues() {

    }


}