package jp.mochili.mochili.model

import io.realm.RealmObject

/**
 * Created by ryotayamagishi on 2017/12/31.
 */
open class User(
        open var userId: String = "",
        open var userName: String = "",
        open var cognitoId: String = "",
        open var password: String = "",
        open var createdAt: String = "",
        open var updatedAt: String = ""
) : RealmObject()