package jp.mochili.mochili.viewmodel

import android.content.DialogInterface
import android.databinding.ObservableField
import android.os.Handler
import android.util.Log
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import io.realm.Realm
import jp.mochili.mochili.contract.SettingViewContract
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.MochiliClient
import kotlin.concurrent.thread

/**
 * Created by ryotayamagishi on 2018/01/01.
 */
class SettingViewModel(val view: SettingViewContract) {

    // databinding
    val userId = ObservableField<String>()
    val userName = ObservableField<String>()

    private val handler: Handler = Handler()
    private var lastUserId: String
    private var lastUserName: String
    private lateinit var updatedUserId: String
    private lateinit var updatedUserName: String

    init {
        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).findFirst()
        lastUserId = user?.userId ?: ""
        lastUserName = user?.userName ?: ""
        realm.close()

        userId.set(lastUserId)
        userName.set(lastUserName)
    }

    // 変更したかどうか、変更があった場合は問題ないかどうかをチェックしてdialogを表示
    fun checkChange(isFirst: Boolean, checkedFun: () -> Unit) {
        updatedUserId = userId.get().trim()
        updatedUserName = userName.get().trim()

        // 変化したかどうか（初回時は絶対に変化したとする）
        val isChanged = isFirst or (lastUserId != updatedUserId) or (lastUserName != updatedUserName)
        // 変化した場合は問題ないかcheck, 変化してない場合は何もせず前の画面に戻る
        if (isChanged) {
            if (checkFormat()) checkUniqueId(checkedFun)
        } else {
            view.onSuperBack()
        }
    }

    // userId,userNameが正しい書式であるかをチェック
    private fun checkFormat(): Boolean {
        if (updatedUserId.isEmpty() or updatedUserName.isEmpty()) {
            formatDialog()
            return false
        }
        return true
    }

    // userIdが一意のものであるかをチェック
    private fun checkUniqueId(checkedFun: () -> Unit) {
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val user = client.userGet("Karl")
                Log.d("test--", user?.userId ?: "nullだよ")
                handler.post(checkedFun)
            } catch(e: Exception) {
                e.stackTrace
            }
        }
    }

    // realm及びサーバーに変更を保存
    fun saveChange(isFirst: Boolean) {
        if (isFirst) {

        } else {

        }
    }

    //region dialog
    fun firstDialog() {
        val title = "はじめに"
        val message = """
                ユーザーIDとユーザー名を登録しましょう！
                （ユーザーIDは変更できないので注意してください。）
            """.trimIndent()
        view.showDialog(title, message)
    }

    private fun formatDialog() {
        val title = "注意"
        val message = """
            ユーザーID,ユーザー名は1〜12文字にしてください。
            """.trimIndent()
        view.showDialog(title, message)
    }

    fun confirmDialog(positiveEvent: (dialog: DialogInterface, which: Int) -> Unit) {
        val title = "注意"
        val message = """
                    ユーザーIDは変更できません。
                    ${updatedUserId}で大丈夫ですか？
                    """.trimIndent()
        view.showDialog(title, message, positiveEvent)
    }
    //endregion
}