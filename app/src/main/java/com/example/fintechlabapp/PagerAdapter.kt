package com.example.fintechlabapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm,lc) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LatestFragment()
            1 -> TopFragment()
            2 -> HotFragment()
            else -> LatestFragment()
        }
    }

    override fun getItemCount(): Int = 3
}