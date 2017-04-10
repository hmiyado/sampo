package com.hmiyado.sampo.view.setting.ui

import android.content.pm.PackageManager
import android.view.View
import android.widget.LinearLayout
import com.hmiyado.sampo.R
import com.hmiyado.sampo.view.common.sampoToolbar
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2017/04/10.
 */
class AboutAppFragmentUi : AnkoComponent<RxFragment> {
    override fun createView(ui: AnkoContext<RxFragment>): View = with(ui) {
        linearLayout(theme = R.style.AppTheme) {
            orientation = LinearLayout.VERTICAL
            sampoToolbar { }
            textView(theme = R.style.AppTheme) {
                text = "さんぽ"
                textSize = 21f
            }
            textView(theme = R.style.AppTheme) {
                val versionName = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_META_DATA).versionName
                text = "バージョン $versionName"
                textSize = 21f
            }
            lparams(width = matchParent)
        }

    }
}