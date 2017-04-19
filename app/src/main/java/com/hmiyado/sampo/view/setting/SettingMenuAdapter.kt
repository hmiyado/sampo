package com.hmiyado.sampo.view.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hmiyado.sampo.R
import com.hmiyado.sampo.domain.setting.SettingMenu

/**
 * Created by hmiyado on 2017/04/19.
 */
class SettingMenuAdapter : BaseAdapter() {
    val items = SettingMenu.values()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        parent ?: return null
        return (convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.result_option_item_layout, parent, false)).apply {
            this as TextView
            text = getText(position)
        }
    }

    private fun getText(position: Int): String {
        return when (getItem(position) as SettingMenu) {
            SettingMenu.ABOUT_APP -> "アプリについて"
            SettingMenu.LICENCE   -> "オープンソースライセンス"
        }
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].ordinal.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}