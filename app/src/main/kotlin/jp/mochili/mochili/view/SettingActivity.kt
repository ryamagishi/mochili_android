package jp.mochili.mochili.view

import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.text.InputType
import android.view.MenuItem
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.SettingViewContract
import jp.mochili.mochili.databinding.ActivitySettingBinding
import jp.mochili.mochili.utils.DialogUtils
import jp.mochili.mochili.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*




class SettingActivity : AppCompatActivity(), SettingViewContract {

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

    // アプリ初回に来た時にはID,nameを登録してほしい旨のダイアログを表示(idのinputを英数字に制限)
    private fun checkFirst() {
        if (isFirst) {
            user_id_edit.setRawInputType(
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            val inputFilter = InputFilter { source, _, _, _, _, _ ->
                if (source.toString().matches("^[0-9a-zA-Z]+$".toRegex())) { source } else { "" }
            }
            val filters = arrayOf(inputFilter)
            user_id_edit.filters = filters.plus(InputFilter.LengthFilter(12))
            viewModel.firstDialog()
        } else {
            user_id_edit.isFocusable = false
        }
    }

    // ID,Nameの変化をcheckして問題なければsaveして戻る
    private fun checkChange() {
        viewModel.checkChange(isFirst) {
            // checkChangeが問題なかった場合に以下のメソッドが実行
            if (isFirst) {
                viewModel.confirmDialog { _, _ ->
                    viewModel.saveChangeAWS(true)
                }
            } else {
                viewModel.saveChangeAWS(false)
            }
        }
    }

    //region SettingViewContract
    // Back
    override fun onSuperBack() {
        super.onBackPressed()
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
