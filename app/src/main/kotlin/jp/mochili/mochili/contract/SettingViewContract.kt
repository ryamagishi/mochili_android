package jp.mochili.mochili.contract

import android.content.DialogInterface

/**
 * Created by ryotayamagishi on 2018/01/05.
 */
interface SettingViewContract {
    fun checkChange()
    fun onSuperBack()
    fun showDialog(title: String, message: String)
    fun showDialog(title: String, message: String,
                   positiveEvent: (dialog: DialogInterface, which: Int) -> Unit)
    fun showDialog(title: String, message: String,
                   positiveEvent: (dialog: DialogInterface, which: Int) -> Unit,
                   negativeEvent: (dialog: DialogInterface, which: Int) -> Unit)
}