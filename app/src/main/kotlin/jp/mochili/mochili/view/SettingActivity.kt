package jp.mochili.mochili.view

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.MenuItem
import jp.mochili.mochili.R
import jp.mochili.mochili.databinding.ActivitySettingBinding
import jp.mochili.mochili.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySettingBinding>(this,
                R.layout.activity_setting)
        viewModel = SettingViewModel(ObservableField(user_id_edit.text.toString()),
                ObservableField(user_name_edit.text.toString()))
        binding.viewmodel = viewModel

        setView()
    }

    private fun setView() {
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "設定"

        checkFirst()
    }

    // 左上のbackと通常backの挙動を合わせる
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    // back押下時の処理
    override fun onBackPressed() {
        checkChange()
    }

    // アプリ初回に来た時にはID,nameを登録してほしい旨のダイアログを表示
    private fun checkFirst() {
        val isFirst = intent.getBooleanExtra("isFirst", false)
        if (isFirst) {
            user_id_edit.setRawInputType(
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

            val title = "はじめに"
            val message = """
                ユーザーIDとユーザー名を登録しましょう！
                （ユーザーIDは変更できないので注意してください。）
            """.trimIndent()
            showDialog(title, message)
        } else {
            user_id_edit.isFocusable = false
        }
    }

    // ID,nameの変化をcheck
    private fun checkChange() {
        if (viewModel.checkChange()) super.onBackPressed()
    }

    // dialogを表示
    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
    }
}
