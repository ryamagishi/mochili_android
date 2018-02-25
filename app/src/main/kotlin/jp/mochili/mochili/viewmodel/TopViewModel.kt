package jp.mochili.mochili.viewmodel

/**
 * Created by ryotayamagishi on 2018/02/25.
 */
class TopViewModel {
    // Friends呼び出しメソッド
    fun getFriends(): MutableList<String> {
        val friends: MutableList<String> = mutableListOf()
        var i = 1
        while (i < 20) {
            friends.add("友達！")
            i++
        }
        return friends
    }
}