package com.example.fintechlabapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fintechlabapp.fragments.BaseFragment
import com.example.fintechlabapp.fragments.RandomFragment

class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm,lc) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
//            0 -> BaseFragment.newInstance("latest")
//            1 -> BaseFragment.newInstance("top")
//            2 -> BaseFragment.newInstance("hot")
//            else -> BaseFragment.newInstance("latest")
            1 -> BaseFragment.newInstance("latest")
            2 -> BaseFragment.newInstance("top")
            3 -> BaseFragment.newInstance("hot")
            else -> RandomFragment()
        }
    }

    override fun getItemCount(): Int = 4
}