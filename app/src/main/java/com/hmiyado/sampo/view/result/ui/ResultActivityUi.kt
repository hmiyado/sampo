package com.hmiyado.sampo.view.result.ui

import android.view.View
import com.hmiyado.sampo.view.result.ResultActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

/**
 * Created by hmiyado on 2017/02/03.
 */
class ResultActivityUi : AnkoComponent<ResultActivity> {
    companion object {
        val ROOT_VIEW_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ResultActivity>) = with(ui) {
        relativeLayout {
            id = ROOT_VIEW_ID
        }
    }
}