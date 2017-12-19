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

    lateinit private var title: String
    private var dataList: MutableList<String> = mutableListOf()

    // instance生成メソッド
    companion object {
        const val ARG_TITLE = "title"
        fun getInstance(title: String): TopFragment {
            val fragment = TopFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        title = bundle.getString(ARG_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)

        initData()
        val recyclerView = view.findViewById(R.id.recyclerview_top) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = TopRecyclerAdapter(recyclerView.context, dataList)

        return view
    }

    private fun initData() {
        var i = 1
        while (i < 20) {
            (dataList as ArrayList<String>).add(title)
            i++
        }
    }
}