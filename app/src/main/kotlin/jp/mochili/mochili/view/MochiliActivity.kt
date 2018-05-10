package jp.mochili.mochili.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.MochiliViewContract
import jp.mochili.mochili.databinding.ActivityMochiliBinding
import jp.mochili.mochili.viewmodel.MochiliViewModel
import kotlinx.android.synthetic.main.activity_create_mochili.*

class MochiliActivity : AppCompatActivity(), MochiliViewContract {

    private lateinit var viewModel: MochiliViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMochiliBinding>(this,
                R.layout.activity_mochili)
        viewModel = MochiliViewModel(this)
        binding.viewmodel = viewModel

        setView()
    }

    private fun setView() {
        // actionbar
        setSupportActionBar(my_toolbar)
        supportActionBar?.title = "持ち物リスト"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 左上のbackと通常backの挙動を合わせる
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
