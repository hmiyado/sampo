package com.hmiyado.sampo.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hmiyado.sampo.view.setting.ui.AboutAppFragmentUi
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoContext

/**
 * Created by hmiyado on 2017/04/10.
 */
class AboutAppFragment : RxFragment() {
    companion object {
        fun newInstance(): AboutAppFragment = AboutAppFragment()
    }

    val ui = AboutAppFragmentUi()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ui.createView(AnkoContext.Companion.create(activity.baseContext, this))
    }
}