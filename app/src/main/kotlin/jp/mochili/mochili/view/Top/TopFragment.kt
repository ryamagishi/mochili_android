package jp.mochili.mochili.view.Top

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import jp.mochili.mochili.R
import jp.mochili.mochili.model.AWS.AWSClient
import jp.mochili.mochili.model.apigateway.MochiliClient
import jp.mochili.mochili.view.Top.TopActivity.FragmentEnum
import kotlin.concurrent.thread


/**
 * Created by ryotayamagishi on 2017/11/28.
 */
class TopFragment : Fragment() {

    private val handler = Handler()
    private lateinit var fragmentEnum: TopActivity.FragmentEnum
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: TopFragmentListener
    private var dataList: MutableList<String> = mutableListOf()

    companion object {
        const val FRAGMENT_ENUM = "fragment_enum"
        // instance生成メソッド
        fun getInstance(topFragmentEnum: TopActivity.FragmentEnum): TopFragment {
            val fragment = TopFragment()
            val bundle = Bundle()
            bundle.putSerializable(FRAGMENT_ENUM, topFragmentEnum)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        fragmentEnum = bundle?.getSerializable(FRAGMENT_ENUM) as TopActivity.FragmentEnum
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)

        recyclerView = view.findViewById(R.id.recyclerview_top) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        initData()

        return view
    }

    // dataListにデータを格納
    private fun initData() {
        when (fragmentEnum) {
            FragmentEnum.MOCHILIS -> {
                thread {
                    try {
                        val credentialsProvider = AWSClient.getCredentialsProvider()
                        val client = ApiClientFactory()
                                .credentialsProvider(credentialsProvider)
                                .build<MochiliClient>(MochiliClient::class.java)
                        val mochilis = client.mymochilisGet("Karl")
                        mochilis.mapTo(dataList) { it.mochiliName }

                        handler.post {
                            recyclerView.adapter = MochiliRecyclerAdapter(recyclerView.context, dataList)
                        }
                    } catch(e: Exception) {
                        e.stackTrace
                    }
                }
            }
            FragmentEnum.FRIENDS -> {
                listener.getFriends {
                    friendNames ->
                    recyclerView.adapter = FriendRecyclerAdapter(recyclerView.context, friendNames)
                }
            }
        }
    }

    //region fragment,activity連携
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement TopFragmentListener")
        }
    }

    interface TopFragmentListener {
        fun getFriends(noticeAdapter: (friendNames: MutableList<String>) -> Unit)
    }
    //endregion
}