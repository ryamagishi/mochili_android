package jp.mochili.mochili.viewmodel

import android.databinding.ObservableField
import android.util.Log
import android.view.View

/**
 * Created by ryotayamagishi on 2018/03/25.
 */
class AddFriendViewModel {
    // databinding
    val userId = ObservableField<String>()

    // databinding
    fun onClickSearch(view: View) {
        Log.d("search", "dekita")
    }
}