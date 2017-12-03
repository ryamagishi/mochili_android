package jp.mochili.mochili.view.Top

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jp.mochili.mochili.R
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.apigateway.MochiliClient
import kotlin.concurrent.thread
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout
import android.support.v4.view.ViewPager
import android.R.menu
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import jp.mochili.mochili.model.IntentUtils


class TopActivity : AppCompatActivity() {

    private var mCoordinatorTabLayout: CoordinatorTabLayout? = null
    private val mImageArray: IntArray? = null
    private val mColorArray: IntArray? = null
    private val mFragments: MutableList<Fragment> = mutableListOf()
    private val mTitles = arrayOf("Android", "iOS", "Web", "Other")
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        //Add the fragment to the viewpager
        initFragments()
        initViewPager()
        //Image array
        val mImageArray = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round)

        mCoordinatorTabLayout = findViewById(R.id.coordinatortablayout) as CoordinatorTabLayout
        mCoordinatorTabLayout?.setTitle("Demo")
                ?.setImageArray(mImageArray)
                ?.setupWithViewPager(mViewPager)



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

    private fun initFragments() {
        mTitles.mapTo(mFragments) { TopFragment.getInstance(it) }
    }

    private fun initViewPager() {
        val mViewPager = findViewById(R.id.vp) as ViewPager
        mViewPager.offscreenPageLimit = 4
        mViewPager.adapter = TopPagerAdapter(supportFragmentManager, mFragments as ArrayList<Fragment>, mTitles)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        when (item.getItemId()) {
            R.id.action_about -> IntentUtils.openUrl(this, "https://github.com/hugeterry/CoordinatorTabLayout")
            R.id.action_about_me -> IntentUtils.openUrl(this, "http://hugeterry.cn/about")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
