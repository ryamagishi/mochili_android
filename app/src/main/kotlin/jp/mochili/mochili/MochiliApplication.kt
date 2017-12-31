package jp.mochili.mochili

import android.app.Application
import jp.mochili.mochili.model.AWS.AWSClient
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlin.concurrent.thread


/**
 * Created by ryotayamagishi on 2017/11/21.
 */

lateinit private var instance: MochiliApplication

fun getApplicationInstance(): MochiliApplication = instance

class MochiliApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // ApplicationのInstanceをセット
        instance = this
        // cognito情報認証
        AWSClient.initCognito()
        // realmの初期化
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}