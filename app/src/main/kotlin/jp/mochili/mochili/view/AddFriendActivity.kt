package jp.mochili.mochili.view

import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.AddFriendViewContract
import jp.mochili.mochili.databinding.ActivityAddFriendBinding
import jp.mochili.mochili.utils.DialogUtils
import jp.mochili.mochili.viewmodel.AddFriendViewModel
import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : AppCompatActivity(), AddFriendViewContract {

    private lateinit var viewModel: AddFriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddFriendBinding>(this,
                R.layout.activity_add_friend)
        viewModel = AddFriendViewModel(this)
        binding.viewmodel = viewModel

        setView()
    }

    private fun setView() {
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "友だち追加"
    }

    //region SettingViewContract
    // finish
    override fun activityFinish() {
        finish()
    }

    // dialogを表示
    override fun showDialog(title: String, message: String) {
        DialogUtils.showDialog(this, title, message)
    }

    // positiveEventのあるdialogを表示
    override fun showDialog(title: String, message: String,
                            positiveEvent: (dialog: DialogInterface, which: Int) -> Unit) {
        DialogUtils.showDialog(this, title, message, positiveEvent)
    }
    //endregion
}
