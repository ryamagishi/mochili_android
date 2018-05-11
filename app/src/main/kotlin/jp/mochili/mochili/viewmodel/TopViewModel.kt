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
import jp.mochili.mochili.model.apigateway.model.Friends
import jp.mochili.mochili.model.apigateway.model.Mochilis
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
            } catch(e: ApiClientException) {
                e.stackTrace
            }
        }
    }

    // Mochilis呼び出しメソッド
    fun getMochilis(noticeAdapter: (mochilis: Mochilis) -> Unit) {
        thread {
            val realm = Realm.getDefaultInstance()
            val user = realm.where(User::class.java).findFirst()

            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                client.mymochilisGet(user?.userId)?.let {
                    handler.post{ noticeAdapter(it) }
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}