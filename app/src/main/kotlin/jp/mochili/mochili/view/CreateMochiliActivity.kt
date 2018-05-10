package jp.mochili.mochili.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import jp.mochili.mochili.R
import jp.mochili.mochili.contract.CreateMochiliViewContract
import jp.mochili.mochili.databinding.ActivityCreateMochiliBinding
import jp.mochili.mochili.viewmodel.CreateMochiliViewModel
import kotlinx.android.synthetic.main.activity_create_mochili.*

class CreateMochiliActivity : AppCompatActivity(), CreateMochiliViewContract {

    private lateinit var viewModel: CreateMochiliViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCreateMochiliBinding>(this,
                R.layout.activity_create_mochili)
        viewModel = CreateMochiliViewModel(this)
        binding.viewmodel = viewModel

        setView()
    }

    private fun setView() {
        // actionbar
        setSupportActionBar(my_toolbar)
        supportActionBar?.title = "持ち物リスト作成"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // mochilimembers
        member_recyclerView.layoutManager = LinearLayoutManager(member_recyclerView.context)
        member_recyclerView.adapter =
                MochiliMemberRecyclerAdapter(member_recyclerView.context, viewModel.mochiliMembers)
    }

    //region menu関連
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // メニューのインフレート
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.save_button -> viewModel.onClickSave { mochiliId: String ->
                val intent = Intent(this, MochiliActivity::class.java)
                intent.putExtra("mochiliId", mochiliId)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    override fun activityFinish() {
        finish()
    }
}
