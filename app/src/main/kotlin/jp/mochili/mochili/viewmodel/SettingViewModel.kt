package jp.mochili.mochili.viewmodel

import android.content.DialogInterface
import android.databinding.ObservableField
import android.os.Handler
import android.text.format.DateFormat
import android.util.Log
import com.amazonaws.mobileconnectors.apigateway.ApiClientException
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import io.realm.Realm
import jp.mochili.mochili.contract.SettingViewContract
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.MochiliClient
import jp.mochili.mochili.model.apigateway.model.Result
import java.util.*
import kotlin.concurrent.thread
import jp.mochili.mochili.model.apigateway.model.User as AWSUser
import kotlin.concurrent.thread as AWSUser


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

        // 変化したかどうか
        val isChanged = (lastUserId != updatedUserId) or (lastUserName != updatedUserName)
        // 変化した場合は問題ないかcheck, 変化してない場合は何もせず前の画面に戻る(初回時は絶対チェック)
        if (isFirst) {
            if (checkFormat()) checkUniqueId(checkedFun)
        } else if (isChanged) {
            if (checkFormat()) {
                confirmChangeDialog(
                        { _, _ -> checkedFun() },
                        { _, _ -> view.onSuperBack() }
                )
            }
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
                val user = client.userGet(updatedUserId)
                if (user.userId == null) handler.post(checkedFun) else handler.post{ uniqueIdDialog() }
            } catch(e: Exception) {
                e.stackTrace
            }
        }
    }

    // realm及びサーバーに変更を保存
    fun saveChangeAWS(isFirst: Boolean) {
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val awsUser = AWSUser()
                awsUser.userId = updatedUserId
                awsUser.userName = updatedUserName
                awsUser.cognitoId = credentialsProvider.identityId
                // 暫定的にpasswordは全てp
                awsUser.password = "p"

                val result: Result
                result =
                        if (isFirst) {
                            client.userPost(awsUser)
                        } else {
                            client.userPut(awsUser)
                        }
                Log.d("awsUserResult", result.status + "****" + result.detail)
                // OKでなければエラーダイアログを表示し再度登録を求める
                if (result.status == "OK") {
                    handler.post { saveChangeRealm(isFirst) }
                } else {
                    handler.post { savingErrorDialog() }
                }
            } catch(e: ApiClientException) {
                e.stackTrace
            }
        }
    }

    fun saveChangeRealm(isFirst: Boolean) {
        // 現在時刻を取得
        val nowDataTime = DateFormat
                .format("yyyy-MM-dd, E, kk:mm:ss", Calendar.getInstance())
                .toString()

        // realmに保存
        if (isFirst) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val user = User()
            user.userId = updatedUserId
            user.userName = updatedUserName
            user.createdAt = nowDataTime
            user.updatedAt = nowDataTime
            realm.copyToRealmOrUpdate(user)
            realm.commitTransaction()
            realm.close()
        } else {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val user = realm.where(User::class.java).findFirst()
            user?.userName = updatedUserName
            user?.updatedAt = nowDataTime
            realm.commitTransaction()
            realm.close()
        }
        view.onSuperBack()
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

    private fun uniqueIdDialog() {
        val title = "注意"
        val message = """
            ${updatedUserId}は使われています。他のユーザーIDをご入力下さい。
            """.trimIndent()
        view.showDialog(title, message)
    }

    // saveError
    private fun savingErrorDialog() {
        val title = "エラー"
        val message = """
            エラーが発生しました。もう一度お試し下さい。
            """.trimIndent()
        view.showDialog(title, message)
    }

    private fun confirmChangeDialog(
            positiveEvent: (dialog: DialogInterface, which: Int) -> Unit,
            negativeEvent: (dialog: DialogInterface, which: Int) -> Unit) {
        val title = "注意"
        val message = """
                    ユーザー名を${updatedUserName}に変更しますか？
                    """.trimIndent()
        view.showDialog(title, message, positiveEvent, negativeEvent)
    }
    //endregion
}