package jp.mochili.mochili.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by ryotayamagishi on 2017/12/31.
 */
open class User(
        @PrimaryKey open var userId: String = "",
        open var userName: String = "",
        open var createdAt: String = "",
        open var updatedAt: String = ""
) : RealmObject()