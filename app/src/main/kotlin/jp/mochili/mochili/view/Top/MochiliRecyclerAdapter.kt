package jp.mochili.mochili.view.Top

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.mochili.mochili.R
import jp.mochili.mochili.model.apigateway.model.Mochilis


/**
 * Created by ryotayamagishi on 2017/11/28.
 */
class MochiliRecyclerAdapter(private val context: Context, private val mochilis: Mochilis)
    : RecyclerView.Adapter<MochiliRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_mochili_top, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textview.text = mochilis[position].mochiliName
    }

    override fun getItemCount(): Int {
        return mochilis.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textview: TextView = view.findViewById(R.id.text_view) as TextView
    }

}