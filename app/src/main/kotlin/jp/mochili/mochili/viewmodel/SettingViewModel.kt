package jp.mochili.mochili.viewmodel

import android.content.DialogInterface
import android.databinding.ObservableField
import io.realm.Realm
import jp.mochili.mochili.contract.SettingViewContract
import jp.mochili.mochili.model.User

/**
 * Created by ryotayamagishi on 2018/01/01.
 */
class SettingViewModel(val view: SettingViewContract) {

    // databinding
    val userId = ObservableField<String>()
    val userName = ObservableField<String>()

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
    fun checkChange(isFirst: Boolean): Boolean {
        updatedUserId = userId.get().trim()
        updatedUserName = userName.get().trim()

        if (isFirst or (lastUserId != updatedUserId) or (lastUserName != updatedUserName)) {
            return checkUniqueId()
        }
        return true
    }

    // userIdが一意のものであるかをチェック
    private fun checkUniqueId(): Boolean {
        return true
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