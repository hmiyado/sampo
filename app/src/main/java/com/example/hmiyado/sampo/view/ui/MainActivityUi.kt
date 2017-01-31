package com.example.hmiyado.sampo.view.ui

import android.view.View
import com.example.hmiyado.sampo.activity.MainActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

/**
 * Created by hmiyado on 2016/08/08.
 */
class MainActivityUi: AnkoComponent<MainActivity> {
    companion object {
        val ROOT_VIEW_ID = View.generateViewId()
    }
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            id = ROOT_VIEW_ID
        }
    }
}