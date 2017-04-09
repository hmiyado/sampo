package com.hmiyado.sampo.view.common.ui

import android.view.View
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

/**
 * Created by hmiyado on 2016/08/08.
 */
class RxActivityUi : AnkoComponent<RxAppCompatActivity> {
    val ROOT_VIEW_ID = View.generateViewId()

    override fun createView(ui: AnkoContext<RxAppCompatActivity>) = with(ui) {
        relativeLayout {
            id = ROOT_VIEW_ID
        }
    }
}