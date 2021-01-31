package com.example.fintechlabapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fintechlabapp.fragments.BaseFragment

class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm,lc) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> BaseFragment.newInstance("latest")
            2 -> BaseFragment.newInstance("top")
            3 -> BaseFragment.newInstance("hot")
            else -> BaseFragment.newInstance("random")
        }
    }

    override fun getItemCount(): Int = 4
}