package com.example.hmiyado.sampo.view.common

import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.view.map.MapActivity
import com.example.hmiyado.sampo.view.result.ResultActivity
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
                        context.startActivity(Intent(context, MapActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        true
                    }
                    R.id.toolbar_menue_result -> {
                        context.startActivity(Intent(context, ResultActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        true
                    }
                    else                      -> false
                }
            }
        }
    }

}