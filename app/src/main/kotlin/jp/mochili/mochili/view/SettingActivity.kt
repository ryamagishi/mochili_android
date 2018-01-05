package jp.mochili.mochili.view

import android.app.PendingIntent.getActivity
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.MenuItem
import jp.mochili.mochili.R
import jp.mochili.mochili.databinding.ActivitySettingBinding
import jp.mochili.mochili.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*
import android.content.DialogInterface
import jp.mochili.mochili.utils.DialogUtils


class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel
    private var isFirst = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySettingBinding>(this,
                R.layout.activity_setting)
        viewModel = SettingViewModel(this)
        binding.viewmodel = viewModel

        isFirst = intent.getBooleanExtra("isFirst", false)

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
        if (isFirst) {
            user_id_edit.setRawInputType(
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

            val title = "はじめに"
            val message = """
                ユーザーIDとユーザー名を登録しましょう！
                （ユーザーIDは変更できないので注意してください。）
            """.trimIndent()
            DialogUtils.showDialog(this, title, message)
        } else {
            user_id_edit.isFocusable = false
        }
    }

    // ID,Nameの変化をcheckして問題なければsaveして戻る
    private fun checkChange() {
        if (viewModel.checkChange(isFirst)) {
            if (isFirst) {
                val title = "注意"
                val message = """
                    ユーザーIDは変更できません。
                    ${user_id_edit.text}で大丈夫ですか？
                    """.trimIndent()
                DialogUtils.showDialog(this, title, message) { _, _ ->
                    viewModel.saveChange(isFirst)
                    super.onBackPressed()
                }
            } else {
                viewModel.saveChange(isFirst)
                super.onBackPressed()
            }
        }
    }
}
