package com.example.fintechlabapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm,lc) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BaseFragment("latest")
            1 -> BaseFragment("top")
            2 -> BaseFragment("hot")
            else -> BaseFragment("latest")
        }
    }

    override fun getItemCount(): Int = 3
}