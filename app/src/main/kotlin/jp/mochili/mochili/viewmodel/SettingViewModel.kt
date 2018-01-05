package jp.mochili.mochili.viewmodel

import android.content.Context
import android.databinding.ObservableField
import io.realm.Realm
import jp.mochili.mochili.model.User

/**
 * Created by ryotayamagishi on 2018/01/01.
 */
class SettingViewModel(val context: Context) {

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
        updatedUserId = userId.get()
        updatedUserName = userName.get()

        if (isFirst or (lastUserId != updatedUserId) or (lastUserName != updatedUserName)) {
            return if (checkFormat()) checkUniqueId() else false
        }
        return true
    }

    // userId,userNameが正しい書式であるかをチェック
    private fun checkFormat(): Boolean {
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
}