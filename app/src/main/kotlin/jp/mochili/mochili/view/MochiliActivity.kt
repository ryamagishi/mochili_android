package jp.mochili.mochili.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.MochiliViewContract
import jp.mochili.mochili.databinding.ActivityMochiliBinding
import jp.mochili.mochili.viewmodel.MochiliViewModel

class MochiliActivity : AppCompatActivity(), MochiliViewContract {

    private lateinit var viewModel: MochiliViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMochiliBinding>(this,
                R.layout.activity_mochili)
        viewModel = MochiliViewModel(this)
        binding.viewmodel = viewModel
    }
}
