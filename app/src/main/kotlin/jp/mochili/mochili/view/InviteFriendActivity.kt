package jp.mochili.mochili.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.InviteFriendContract
import jp.mochili.mochili.databinding.ActivityInviteFriendBinding
import jp.mochili.mochili.viewmodel.InviteFriendViewModel

class InviteFriendActivity : AppCompatActivity(), InviteFriendContract {

    private lateinit var viewModel: InviteFriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityInviteFriendBinding>(this,
                R.layout.activity_invite_friend)
        viewModel = InviteFriendViewModel(this)
        binding.viewmodel = viewModel
    }
}
