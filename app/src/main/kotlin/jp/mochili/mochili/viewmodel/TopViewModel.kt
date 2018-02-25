package jp.mochili.mochili.viewmodel

import android.os.Handler
import com.amazonaws.mobileconnectors.apigateway.ApiClientException
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.apigateway.MochiliClient
import kotlin.concurrent.thread

/**
 * Created by ryotayamagishi on 2018/02/25.
 */
class TopViewModel {

    private val handler: Handler = Handler()

    // Friends呼び出しメソッド
    fun getFriends(noticeAdapter: (friendNames: MutableList<String>) -> Unit) {
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val friends = client.myfriendsGet("android")
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