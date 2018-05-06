package jp.mochili.mochili.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.mochili.mochili.R
import jp.mochili.mochili.databinding.ActivityCreateMochiliBinding
import jp.mochili.mochili.viewmodel.CreateMochiliViewModel

class CreateMochiliActivity : AppCompatActivity() {

    private lateinit var viewModel: CreateMochiliViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCreateMochiliBinding>(this,
                R.layout.activity_create_mochili)
        viewModel = CreateMochiliViewModel()
        binding.viewmodel = viewModel
    }
}
