package jp.mochili.mochili.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.mochili.mochili.R
import jp.mochili.mochili.model.apigateway.model.Friends
import jp.mochili.mochili.model.apigateway.model.FriendsItem


/**
 * Created by ryotayamagishi on 2018/05/11.
 */
class InviteFriendRecyclerAdapter(private val context: Context, private val friends: Friends)
    : RecyclerView.Adapter<InviteFriendRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_friend, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textview.text = friends[position].friendName
        holder.itemView.setOnClickListener {
            if (context is InviteFriendRecyclerAdapterListener) {
                val position = holder.adapterPosition
                context.returnInvitedFriend(friends[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textview: TextView = view.findViewById(R.id.text_view) as TextView
    }

    interface InviteFriendRecyclerAdapterListener {
        fun returnInvitedFriend (invitedFriend: FriendsItem)
    }
}