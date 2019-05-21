package phuchh.sunasterisk.projectmoviedb.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

class DetailsViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
    private val fragments: MutableList<Fragment> = mutableListOf()
    private var titles: MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence? = titles[position]
    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }
}