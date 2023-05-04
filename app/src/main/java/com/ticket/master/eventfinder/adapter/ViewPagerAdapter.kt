package com.ticket.master.eventfinder.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

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