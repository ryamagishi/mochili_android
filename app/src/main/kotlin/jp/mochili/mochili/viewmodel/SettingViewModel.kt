package jp.mochili.mochili.viewmodel

import android.databinding.ObservableField
import android.util.Log

/**
 * Created by ryotayamagishi on 2018/01/01.
 */
class SettingViewModel(val userId: ObservableField<String>, val userName: ObservableField<String>) {

    fun checkChange(): Boolean {
        Log.d("test--id", userId.get())
        Log.d("test--name", userName.get())
        return true
    }
}