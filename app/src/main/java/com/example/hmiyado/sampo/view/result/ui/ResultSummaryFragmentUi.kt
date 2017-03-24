package com.example.hmiyado.sampo.view.result.ui

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.example.hmiyado.sampo.view.common.sampoToolbar
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2017/03/01.
 */
class ResultSummaryFragmentUi : AnkoComponent<RxFragment> {
    companion object {
        val totalDistanceTextViewId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<RxFragment>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            sampoToolbar { }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    textColor = Color.BLACK
                    text = "さんぽ"
                }
                textView {
                    textColor = Color.BLACK
                    text = "総距離"
                }
                textView {
                    id = totalDistanceTextViewId
                    textColor = Color.BLACK
                    text = "??? メートル"
                }
            }
        }
    }
}