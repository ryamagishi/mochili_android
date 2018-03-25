package jp.mochili.mochili.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.mochili.mochili.R
import jp.mochili.mochili.databinding.ActivityAddFriendBinding
import jp.mochili.mochili.viewmodel.AddFriendViewModel
import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : AppCompatActivity() {

    private lateinit var viewModel: AddFriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddFriendBinding>(this,
                R.layout.activity_add_friend)
        viewModel = AddFriendViewModel()
        binding.viewmodel = viewModel

        setView()
    }

    private fun setView() {
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "設定"
    }
}
