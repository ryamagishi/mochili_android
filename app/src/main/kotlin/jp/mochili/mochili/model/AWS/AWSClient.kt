package jp.mochili.mochili.model.AWS

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import jp.mochili.mochili.getApplicationInstance

/**
 * Created by ryotayamagishi on 2017/11/21.
 */
object AWSClient {

    private lateinit var credentialsProvider: CognitoCachingCredentialsProvider

    // Cognitoの認証
    fun initCognito(){
        credentialsProvider = CognitoCachingCredentialsProvider(
                getApplicationInstance().applicationContext, // Context
                "ap-northeast-1:310fdab8-aa89-4c9e-ab6b-4eaabac06754", // Identity Pool ID
                Regions.AP_NORTHEAST_1 // Region
        )
    }

    // credentialsProviderの取得
    fun getCredentialsProvider(): CognitoCachingCredentialsProvider = credentialsProvider

}