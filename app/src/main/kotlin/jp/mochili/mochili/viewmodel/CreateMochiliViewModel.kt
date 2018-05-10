package jp.mochili.mochili.viewmodel

import android.databinding.ObservableField
import android.os.Handler
import android.util.Log
import android.view.View
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import io.realm.Realm
import jp.mochili.mochili.contract.CreateMochiliViewContract
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.MochiliClient
import jp.mochili.mochili.model.apigateway.model.Friends
import jp.mochili.mochili.model.apigateway.model.FriendsItem
import jp.mochili.mochili.model.apigateway.model.Mochili
import jp.mochili.mochili.model.apigateway.model.Result
import kotlin.concurrent.thread
import jp.mochili.mochili.model.apigateway.model.User as Member

/**
 * Created by ryotayamagishi on 2018/05/07.
 */
class CreateMochiliViewModel(private val createMochiliView: CreateMochiliViewContract) {

    // databinding
    val mochiliName = ObservableField<String>()
    val memberNumberText = ObservableField<String>()

    private val handler: Handler = Handler()
    val mochiliMembers = Friends()
    private var memberNumber: Int = 1
        set(value) {
            memberNumberText.set("メンバー $value 名")
        }

    init {
        // realmよりuserNameを取得しとく、nullだったらactivityをfinish
        val member = FriendsItem()
        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).findFirst()
        if (user?.userId == null) {
            createMochiliView.activityFinish()
        } else {
            member.friendId = user.userId
            member.friendName = user.userName
        }
        realm.close()

        // とりあえずmochiliMemberに1名(自分)追加
        mochiliMembers.add(member)
        memberNumberText.set("メンバー $memberNumber 名")
    }

    // addボタン押下時メソッド
    fun onClickAdd(view: View) {
        createMochiliView.startInviteFriendActivity()
    }

    // saveボタン押下時メソッド最後に遷移
    fun onClickSave(startMochiliActivity: (String) -> Unit ) {
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val mochili = Mochili()
                mochili.createrId = mochiliMembers[0].friendId
                mochili.mochiliName = mochiliName.get()
                val mochiliMemberList = ArrayList<String>()
                mochiliMembers.forEach { mochiliMemberList.add(it.friendId) }
                mochili.mochiliMembers = mochiliMemberList

                //　保存してmochiliIdを取得
                val result: Result = client.mochiliPost(mochili)
                Log.d("mochiliResult", result.status + "****" + result.detail)
                if (result.status == "OK") {
                    handler.post {
                        val mochiliId = result.detail
                        startMochiliActivity(mochiliId)
                    }
                } else {
                    // TODO
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    // mochiliMemberを追加
    fun addMochiliMember(friendId: String, friendName: String) {
        val member = FriendsItem()
        member.friendId = friendId
        member.friendName = friendName
        mochiliMembers.add(member)
        memberNumber = mochiliMembers.size
    }
}