package com.hmiyado.sampo.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hmiyado.sampo.view.setting.ui.LicenseFragmentUi
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoContext

/**
 * Created by hmiyado on 2017/04/10.
 */
class LicenseFragment : RxFragment() {
    companion object {
        fun newInstance(): LicenseFragment = LicenseFragment()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LicenseFragmentUi().createView(AnkoContext.create(activity.baseContext, this))
    }
}