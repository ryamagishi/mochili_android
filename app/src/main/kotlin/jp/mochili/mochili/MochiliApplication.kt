package jp.mochili.mochili

import android.app.Application
import jp.mochili.mochili.model.AWS.AWSClient
import android.util.Log
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

        thread { Log.d("cognitoId", AWSClient.getCredentialsProvider().identityId) }
    }
}