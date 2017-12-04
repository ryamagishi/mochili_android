package jp.mochili.mochili.view.Top

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jp.mochili.mochili.R
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.apigateway.MochiliClient
import kotlin.concurrent.thread
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import jp.mochili.mochili.model.IntentUtils
import kotlinx.android.synthetic.main.activity_top.*


class TopActivity : AppCompatActivity() {

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val titles = arrayOf("持ち物リスト", "友達", "Web", "Other")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        setView()

        // awstest
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

    private fun setView() {
        //Add the fragment to the viewpager
        initFragments()
        initViewPager()

        //Image array
        val mImageArray = intArrayOf(
                R.mipmap.bg_android,
                R.mipmap.bg_ios,
                R.mipmap.bg_js,
                R.mipmap.bg_other)

        val mColorArray = intArrayOf(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light)

        layout_top.setTitle("mochili")
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(viewpager_top)
    }

    private fun initFragments() {
        titles.mapTo(fragments) { TopFragment.getInstance(it) }
    }

    private fun initViewPager() {
        viewpager_top.offscreenPageLimit = 4
        viewpager_top.adapter = TopPagerAdapter(supportFragmentManager, fragments as ArrayList<Fragment>, titles)
    }

    //region MenuOptions

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        when (item.itemId) {
            R.id.action_about -> IntentUtils.openUrl(this, "https://github.com/hugeterry/CoordinatorTabLayout")
            R.id.action_about_me -> IntentUtils.openUrl(this, "http://hugeterry.cn/about")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //endregion
}
