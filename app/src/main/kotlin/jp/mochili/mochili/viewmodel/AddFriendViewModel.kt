package jp.mochili.mochili.viewmodel

import android.content.DialogInterface
import android.databinding.ObservableField
import android.os.Handler
import android.util.Log
import android.view.View
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import io.realm.Realm
import jp.mochili.mochili.contract.AddFriendViewContract
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.MochiliClient
import jp.mochili.mochili.model.apigateway.model.Friend
import jp.mochili.mochili.model.apigateway.model.Result
import kotlin.concurrent.thread

/**
 * Created by ryotayamagishi on 2018/03/25.
 */
class AddFriendViewModel(private val addFriendView: AddFriendViewContract) {
    // databinding
    val friendId = ObservableField<String>()

    private lateinit var userId: String
    private val handler: Handler = Handler()

    init {
        // realmよりuserIdを取得しとく、nullだったらactivityをfinish
        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).findFirst()
        if (user?.userId == null) { addFriendView.activityFinish() } else userId = user.userId
        realm.close()
    }

    // databinding Userを検索してダイアログを表示
    fun onClickSearch(view: View) {
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val user = client.userGet(friendId.get().trim())
                if (user.userId == null) handler.post{ noneUserDialog() }
                else handler.post{ confirmDialog(user.userName) { _, _ -> addFriend()} }
            } catch(e: Exception) {
                e.stackTrace
            }
        }
    }

    // friend追加
    private fun addFriend() {
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val friend = Friend()
                friend.userId = userId
                friend.friendId = friendId.get().trim()
                val result: Result = client.friendPost(friend)
                Log.d("FriendResult", result.status + "****" + result.detail)
            } catch(e: Exception) {
                e.stackTrace
            }
        }
    }

    //region dialog
    private fun noneUserDialog() {
        val title = "「${friendId.get().trim()}」で一致するユーザーはいませんでした。"
        val message = """
                ・ユーザー名ではなくユーザーIDで検索してください。
                ・入力したユーザーIDが正しいか確認してください。
                """.trimIndent()
        addFriendView.showDialog(title, message)
    }

    private fun confirmDialog(userName: String,
                              positiveEvent: (dialog: DialogInterface, which: Int) -> Unit) {
        val title = "確認"
        val message = """
                    「${userName}」さんを友達に追加しますか？
                    """.trimIndent()
        addFriendView.showDialog(title, message, positiveEvent)
    }
    //endregion
}