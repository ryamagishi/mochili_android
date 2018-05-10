package jp.mochili.mochili.viewmodel

import android.os.Handler
import com.amazonaws.mobileconnectors.apigateway.ApiClientException
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import io.realm.Realm
import jp.mochili.mochili.contract.InviteFriendContract
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.MochiliClient
import jp.mochili.mochili.model.apigateway.model.Friends
import kotlin.concurrent.thread

/**
 * Created by ryotayamagishi on 2018/05/11.
 */
class InviteFriendViewModel(mochiliView: InviteFriendContract) {

    private val handler: Handler = Handler()

    // Friends呼び出しメソッド
    fun getFriends(noticeAdapter: (friends: Friends) -> Unit) {
        thread {
            val realm = Realm.getDefaultInstance()
            val user = realm.where(User::class.java).findFirst()

            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                client.myfriendsGet(user?.userId)?.let {
                    handler.post { noticeAdapter(it) }
                }
            } catch (e: ApiClientException) {
                e.stackTrace
            }
        }
    }
}