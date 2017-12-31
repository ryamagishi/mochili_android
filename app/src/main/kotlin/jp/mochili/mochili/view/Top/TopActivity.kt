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
    lateinit private var titles: Array<String>

    // viewpagerを管理するenum
    enum class FragmentEnum(val id: Int, val title: String, val image: Int, val color: Int) {
        MOCHILIS(1, "持ち物リスト", R.mipmap.bg_android, android.R.color.holo_blue_light),
        FRIENDS(2, "友だち", R.mipmap.bg_ios, android.R.color.holo_red_light);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        setView()
    }

    private fun setView() {
        // Title array
        val titleList = mutableListOf<String>()
        FragmentEnum.values().mapTo(titleList) { it.title }
        titles = titleList.toTypedArray()

        // Image array
        val imageList = mutableListOf<Int>()
        FragmentEnum.values().mapTo(imageList) { it.image }
        val images = imageList.toIntArray()

        // Color Array
        val colorList = mutableListOf<Int>()
        FragmentEnum.values().mapTo(colorList) { it.color }
        val colors = colorList.toIntArray()

        //Add the fragment to the viewpager
        initFragments()
        initViewPager()

        layout_top.setTitle("mochili")
                .setImageArray(images, colors)
                .setupWithViewPager(viewpager_top)
    }

    private fun initFragments() {
        FragmentEnum.values().mapTo(fragments) { TopFragment.getInstance(it) }
    }

    private fun initViewPager() {
        viewpager_top.offscreenPageLimit = FragmentEnum.values().size
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
