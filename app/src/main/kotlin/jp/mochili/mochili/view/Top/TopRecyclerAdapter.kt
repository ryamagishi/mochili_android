package jp.mochili.mochili.view.Top

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.mochili.mochili.R


/**
 * Created by ryotayamagishi on 2017/11/28.
 */
class TopRecyclerAdapter(private val context: Context, private val datas: List<String>):
        RecyclerView.Adapter<TopRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv.text = datas[position]
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView = view.findViewById(R.id.tv_num) as TextView
    }

}