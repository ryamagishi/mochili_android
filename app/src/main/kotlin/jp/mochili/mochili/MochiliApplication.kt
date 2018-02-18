package jp.mochili.mochili

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import jp.mochili.mochili.model.AWS.AWSClient


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
        // realmの初期化(migration注意)
        Realm.init(this)
        val realmConfiguration = RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}