package jp.mochili.mochili.view.Top

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by ryotayamagishi on 2017/11/28.
 */
class TopPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    lateinit private var mFragments: ArrayList<Fragment>
    lateinit private var mTitles: Array<String>

    constructor(fm: FragmentManager, mFragments: ArrayList<Fragment>, mTitles: Array<String>): this(fm) {
        this.mFragments = mFragments
        this.mTitles = mTitles
    }


    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }
}