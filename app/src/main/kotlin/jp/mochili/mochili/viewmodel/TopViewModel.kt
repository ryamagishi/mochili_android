package jp.mochili.mochili.viewmodel

import android.os.Handler
import android.view.View
import com.amazonaws.mobileconnectors.apigateway.ApiClientException
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import io.realm.Realm
import jp.mochili.mochili.contract.TopViewContract
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.MochiliClient
import kotlin.concurrent.thread

/**
 * Created by ryotayamagishi on 2018/02/25.
 */
class TopViewModel(private val topView: TopViewContract) {

    private val handler: Handler = Handler()

    // addボタン押下時メソッド
    fun onClickAdd(view: View) {
        topView.startAddItem()
    }

    // Friends呼び出しメソッド
    fun getFriends(noticeAdapter: (friendNames: MutableList<String>) -> Unit) {
        thread {
            val realm = Realm.getDefaultInstance()
            val user = realm.where(User::class.java).findFirst()

            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val friends = client.myfriendsGet(user?.userId)
                val friendNames: MutableList<String> = mutableListOf()
                friends.mapTo(friendNames) { it.friendName }
                handler.post {
                    noticeAdapter(friendNames)
                }
            } catch(e: ApiClientException) {
                e.stackTrace
            }
        }
    }
}