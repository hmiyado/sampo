package com.example.hmiyado.sampo.view.result.ui

import android.view.View
import android.widget.LinearLayout
import com.example.hmiyado.sampo.view.common.sampoToolbar
import com.example.hmiyado.sampo.view.result.ResultMenuFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.listView

/**
 * Created by hmiyado on 2017/02/05.
 */
class ResultFragmentUi : AnkoComponent<ResultMenuFragment> {
    companion object {
        val listViewId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ResultMenuFragment>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            sampoToolbar { }
            listView {
                id = listViewId
            }
        }
    }
}