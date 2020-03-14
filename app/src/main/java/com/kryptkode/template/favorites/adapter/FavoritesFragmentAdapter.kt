package com.kryptkode.template.favorites.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by kryptkode on 3/12/2020.
 */
class FavoritesFragmentAdapter(private val fragmentList: List<Fragment>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}