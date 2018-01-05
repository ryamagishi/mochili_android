package jp.mochili.mochili.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


/**
 * Created by ryotayamagishi on 2017/12/02.
 */
object IntentUtils {
    fun openUrl(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}