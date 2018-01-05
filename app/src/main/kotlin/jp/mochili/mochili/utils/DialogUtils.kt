package jp.mochili.mochili.utils

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

/**
 * Created by ryotayamagishi on 2018/01/05.
 */
object DialogUtils {
    // dialogを表示
    fun showDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
    }

    // positiveEventのあるdialogを表示
    fun showDialog(context: Context, title: String, message: String,
                           positiveEvent: (dialog: DialogInterface, which: Int) -> Unit) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", positiveEvent)
                .setNegativeButton("Cancel", null)
                .show()
    }
}