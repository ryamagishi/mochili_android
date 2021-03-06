package jp.mochili.mochili.view.Top

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.TopViewContract
import jp.mochili.mochili.databinding.ActivityTopBinding
import jp.mochili.mochili.model.User
import jp.mochili.mochili.model.apigateway.model.Friends
import jp.mochili.mochili.model.apigateway.model.Mochilis
import jp.mochili.mochili.view.AddFriendActivity
import jp.mochili.mochili.view.CreateMochiliActivity
import jp.mochili.mochili.view.SettingActivity
import jp.mochili.mochili.viewmodel.TopViewModel
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity(), TopViewContract, TopFragment.TopFragmentListener {

    private lateinit var viewModel: TopViewModel
    private val fragments: MutableList<TopFragment> = mutableListOf()
    private lateinit var titles: Array<String>

    // viewpagerを管理するenum
    enum class FragmentEnum(val index: Int, val title: String, val image: Int, val color: Int) {
        MOCHILIS(0, "共有メモ", R.mipmap.bg_android, android.R.color.holo_blue_light),
        FRIENDS(1, "友だち", R.mipmap.bg_ios, android.R.color.holo_red_light);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTopBinding>(this,
                R.layout.activity_top)
        viewModel = TopViewModel(this)
        binding.viewmodel = viewModel

        // UserId登録済かチェック
        checkRegister()

        // fragmentをセット
        setView()
    }

    override fun onResume() {
        fragments.forEach { it.initData() }
        super.onResume()
    }

    //region Fragment関連
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
    //endregion

    //region FragmentListener関連
    override fun getFriends(noticeAdapter: (friends: Friends) -> Unit) {
        viewModel.getFriends(noticeAdapter)
    }

    override fun getMochilis(noticeAdapter: (mochilis: Mochilis) -> Unit) {
        viewModel.getMochilis(noticeAdapter)
    }
    //endregion

    //region MenuOptions

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.library -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //endregion

    //region UserRegister確認
    // 登録されていなければ新規登録、登録されていればcognitoId更新
    private fun checkRegister() {
        val realm = Realm.getDefaultInstance()
        val user = realm.where(User::class.java).findFirst()
        if (user == null) {
            val intent = Intent(this, SettingActivity::class.java)
            intent.putExtra("isFirst", true)
            startActivity(intent)
        }
        realm.close()
    }
    //endregion

    //region TopViewContract
    override fun startAddItem() {
        val intent: Intent = when (viewpager_top.currentItem) {
            FragmentEnum.MOCHILIS.index -> {
                Intent(this, CreateMochiliActivity::class.java)
            }
            FragmentEnum.FRIENDS.index -> {
                Intent(this, AddFriendActivity::class.java)
            }
            // 何にも当てはまらなかったらFriend追加画面を出しておく
            else -> {
                Intent(this, AddFriendActivity::class.java)
            }
        }
        startActivity(intent)
    }
    //endregion
}