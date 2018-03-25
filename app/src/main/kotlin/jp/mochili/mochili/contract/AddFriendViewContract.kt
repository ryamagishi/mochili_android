package jp.mochili.mochili.contract

import android.content.DialogInterface

/**
 * Created by ryotayamagishi on 2018/03/25.
 */
interface AddFriendViewContract {
    fun activityFinish()
    fun showDialog(title: String, message: String)
    fun showDialog(title: String, message: String,
                   positiveEvent: (dialog: DialogInterface, which: Int) -> Unit)
}