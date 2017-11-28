package jp.mochili.mochili.view.Top

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jp.mochili.mochili.R
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.apigateway.MochiliClient
import kotlin.concurrent.thread


class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        // test
        thread {
            try {
                val credentialsProvider = AWSClient.getCredentialsProvider()
                val client = ApiClientFactory()
                        .credentialsProvider(credentialsProvider)
                        .build<MochiliClient>(MochiliClient::class.java)
                val mochilis = client.mymochilisGet("Karl")
                val mochili = mochilis[0]
                Log.d("test", mochili.mochiliId)
            } catch(e: Exception) {
                e.stackTrace
            }
        }

    }
}
