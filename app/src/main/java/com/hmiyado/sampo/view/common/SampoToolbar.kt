package com.hmiyado.sampo.view.common

import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import com.hmiyado.sampo.R
import com.hmiyado.sampo.view.map.MapActivity
import com.hmiyado.sampo.view.result.ResultActivity
import com.hmiyado.sampo.view.setting.SettingActivity
import org.jetbrains.anko.backgroundColor

/**
 * Created by hmiyado on 2017/02/03.
 */
class SampoToolbar(context: Context?) : Toolbar(context) {

    init {
        title = "さんぽ"
        if (context != null) {
            val outVal = TypedValue()
            context.theme.resolveAttribute(android.R.attr.actionBarSize, outVal, true)
            minimumHeight = outVal.density
            context.theme.resolveAttribute(android.R.attr.colorPrimary, outVal, true)
            backgroundColor = outVal.data
            inflateMenu(R.menu.toolbar_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.toolbar_menu_map     -> {
                        startActivity(context, MapActivity::class.java)
                        true
                    }
                    R.id.toolbar_menu_result  -> {
                        startActivity(context, ResultActivity::class.java)
                        true
                    }
                    R.id.toolbar_menu_setting -> {
                        startActivity(context, SettingActivity::class.java)
                        true
                    }
                    else                      -> false
                }
            }
        }
    }

    private fun startActivity(context: Context, klass: Class<*>) {
        context.startActivity(Intent(context, klass).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

}