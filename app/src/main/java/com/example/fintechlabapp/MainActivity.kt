package com.example.fintechlabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.fintechlabapp.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewpager)
        tabLayout = findViewById(R.id.tabLayout)

        setupAdapter()
        setupTabLayout()
    }

    private fun setupAdapter() {
        val pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter
        viewPager.setPageTransformer(MarginPageTransformer(500));
    }

    private fun setupTabLayout(){
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Random"
                1 -> tab.text = "Latest"
                2 -> tab.text = "Top"
                3 -> tab.text = "Hot"
            }
        }.attach()
    }

}