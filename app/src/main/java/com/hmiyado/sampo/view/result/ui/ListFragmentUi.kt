package com.hmiyado.sampo.view.result.ui

import android.view.View
import android.widget.LinearLayout
import com.hmiyado.sampo.view.common.sampoToolbar
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.listView

/**
 * Created by hmiyado on 2017/02/05.
 */
class ListFragmentUi : AnkoComponent<RxFragment> {
    val listViewId = View.generateViewId()

    override fun createView(ui: AnkoContext<RxFragment>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            sampoToolbar { }
            listView {
                id = listViewId
            }
        }
    }
}