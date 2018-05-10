package jp.mochili.mochili.viewmodel

import android.databinding.ObservableField
import android.view.View
import io.realm.Realm
import jp.mochili.mochili.contract.CreateMochiliViewContract
import jp.mochili.mochili.model.User

/**
 * Created by ryotayamagishi on 2018/05/07.
 */
class CreateMochiliViewModel(private val addFriendView: CreateMochiliViewContract) {

    // databinding
    val mochiliName = ObservableField<String>()
    val memberNumberText = ObservableField<String>()

    val mochiliMembers = mutableListOf<String>()
    private lateinit var userName: String
    private var memberNumber: Int = 1
        set(value) {
            memberNumberText.set("メンバー $value 名")
        }

    init {
        // realmよりuserNameを取得しとく、nullだったらactivityをfinish
        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).findFirst()
        if (user?.userId == null) {
            addFriendView.activityFinish()
        } else userName = user.userName
        realm.close()

        // とりあえずmochiliMemberに1名(自分)追加
        mochiliMembers.add(userName)
        memberNumberText.set("メンバー $memberNumber 名")
    }

    // addボタン押下時メソッド
    fun onClickAdd(view: View) {
//        topView.startAddItem()
    }
}