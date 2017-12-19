package jp.mochili.mochili.model

import jp.mochili.mochili.R

/**
 * Created by ryotayamagishi on 2017/12/05.
 */
enum class TopFragmentEnum(val id: Int, val title: String, val image: Int, val color: Int) {
    MOCHILIS(1, "持ち物リスト", R.mipmap.bg_android, android.R.color.holo_blue_light),
    FRIENDS(2, "友だち", R.mipmap.bg_ios, android.R.color.holo_red_light);
}