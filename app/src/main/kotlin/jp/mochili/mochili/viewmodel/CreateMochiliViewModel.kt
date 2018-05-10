package jp.mochili.mochili.viewmodel

import android.databinding.ObservableField
import android.view.View
import io.realm.Realm
import jp.mochili.mochili.contract.CreateMochiliViewContract
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.model.Friends
import jp.mochili.mochili.model.apigateway.model.FriendsItem
import jp.mochili.mochili.model.apigateway.model.User as Member

/**
 * Created by ryotayamagishi on 2018/05/07.
 */
class CreateMochiliViewModel(private val createMochiliView: CreateMochiliViewContract) {

    // databinding
    val mochiliName = ObservableField<String>()
    val memberNumberText = ObservableField<String>()

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
    fun onClickSave(startMochiliActivity:(String) -> Unit ) {
        //　保存してmochiliIdを取得
        val mochiliId = "test"

        // 作成したmochiliのactivityへ移動
        startMochiliActivity(mochiliId)
    }

    // mochiliMmeberを追加
    fun addMochiliMember(userId: String) {

    }
}