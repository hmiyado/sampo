package com.example.hmiyado.sampo.view.common

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.TypedValue
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
        }
    }

}