package jp.mochili.mochili.view.Top

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by ryotayamagishi on 2017/11/28.
 */
class TopPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var titles: Array<String>

    constructor(fragmentManager: FragmentManager, fragments: ArrayList<Fragment>, titles: Array<String>)
            : this(fragmentManager) {
        this.fragments = fragments
        this.titles = titles
    }


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}