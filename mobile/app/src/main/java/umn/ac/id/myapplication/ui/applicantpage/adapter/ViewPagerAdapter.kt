package umn.ac.id.myapplication.ui.applicantpage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class ViewPagerAdapter(fragment: FragmentActivity)
    : FragmentStateAdapter(fragment) {

    private val lf = ArrayList<Fragment>()

    override fun getItemCount(): Int {
        return lf.size
    }

    override fun createFragment(position: Int): Fragment {
        return lf[position]
    }

    fun addFragment(fragment: Fragment?) {
        lf.add(fragment!!)
    }

}