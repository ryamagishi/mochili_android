package jp.mochili.mochili.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.mochili.mochili.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setView()
    }

    private fun setView() {
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
