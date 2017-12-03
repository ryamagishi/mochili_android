package jp.mochili.mochili.view.Top

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import jp.mochili.mochili.R


/**
 * Created by ryotayamagishi on 2017/11/28.
 */
class TopFragment : Fragment() {

    lateinit private var mRecyclerView: RecyclerView
    private var mDatas: MutableList<String> = mutableListOf()


    private var mTitle: String? = null



    companion object {
        const val ARG_TITLE = "title"
        fun getInstance(title: String): TopFragment {
            val fra = TopFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TITLE, title)
            fra.arguments = bundle
            return fra
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        mTitle = bundle.getString(ARG_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        initData()
        mRecyclerView = v.findViewById(R.id.recyclerview) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(mRecyclerView.context)
        mRecyclerView.adapter = TopRecyclerAdapter(mRecyclerView.context, mDatas)

        return v
    }

    private fun initData() {
        var i: Int = 1
        while (i < 20) {
            (mDatas as ArrayList<String>).add(mTitle + i.toChar())
            i++
        }
    }
}