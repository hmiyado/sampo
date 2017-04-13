package com.hmiyado.sampo.view.setting.ui

import android.view.View
import android.widget.LinearLayout
import com.hmiyado.sampo.R
import com.hmiyado.sampo.view.common.sampoToolbar
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.webView

/**
 * Created by hmiyado on 2017/04/10.
 */
class LicenseFragmentUi : AnkoComponent<RxFragment> {
    override fun createView(ui: AnkoContext<RxFragment>): View = with(ui) {
        linearLayout(theme = R.style.AppTheme) {
            orientation = LinearLayout.VERTICAL
            sampoToolbar { }
            webView {
                loadUrl("file:///android_asset/licenses.html")
            }
        }
    }
}