package com.ticket.master.eventfinder.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ticket.master.eventfinder.home.FavoritesFragment
import com.ticket.master.eventfinder.search.SearchFragment

class ViewPagerAdapter( fm: FragmentManager,val fragmentItems:MutableList<FragmentItems>) :
    FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentItems[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentItems[position].title
    }
}

data class FragmentItems(val fragment: Fragment, val title: String)