package jp.mochili.mochili.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.InviteFriendContract
import jp.mochili.mochili.databinding.ActivityInviteFriendBinding
import jp.mochili.mochili.model.apigateway.model.FriendsItem
import jp.mochili.mochili.viewmodel.InviteFriendViewModel
import kotlinx.android.synthetic.main.activity_invite_friend.*



class InviteFriendActivity : AppCompatActivity(),
        InviteFriendContract,
        InviteFriendRecyclerAdapter.InviteFriendRecyclerAdapterListener {

    private lateinit var viewModel: InviteFriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityInviteFriendBinding>(this,
                R.layout.activity_invite_friend)
        viewModel = InviteFriendViewModel(this)
        binding.viewmodel = viewModel

        setView()
    }

    private fun setView() {
        // actionbar
        setSupportActionBar(my_toolbar)
        supportActionBar?.title = "招待"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // inviteFriends
        invite_friend_recyclerView.layoutManager = LinearLayoutManager(invite_friend_recyclerView.context)
        viewModel.getFriends {
            invite_friend_recyclerView.adapter =
                    InviteFriendRecyclerAdapter(invite_friend_recyclerView.context, it)
        }
    }

    // 左上のbackと通常backの挙動を合わせる
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun returnInvitedFriend(invitedFriend: FriendsItem) {
        // 送信データの準備
        val arrayListData = ArrayList<String>()
        arrayListData.add(invitedFriend.friendId)
        arrayListData.add(invitedFriend.friendName)

        val intent = Intent()
        intent.putStringArrayListExtra("FriendId,FriendName", arrayListData)
        setResult(RESULT_OK, intent)
        finish()
    }
}
