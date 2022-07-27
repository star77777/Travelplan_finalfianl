package com.example.travelplan_finalfianl.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.travelplan_finalfianl.fragments.CalendarListFragment
import com.example.travelplan_finalfianl.fragments.MovementFragment
import com.example.travelplan_finalfianl.fragments.SettingsFragment

class MainViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CalendarListFragment()
            1 -> MovementFragment()
            else -> SettingsFragment()
        }
    }
}