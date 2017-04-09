package com.hmiyado.sampo.view.result.ui

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.hmiyado.sampo.view.common.sampoToolbar
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2017/03/01.
 */
class ResultSummaryFragmentUi : AnkoComponent<RxFragment> {
    val totalDistanceTextViewId = View.generateViewId()
    val dailyScoreTextViewId = View.generateViewId()

    override fun createView(ui: AnkoContext<RxFragment>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            sampoToolbar { }
            linearLayout {
                textView {
                    textColor = Color.BLACK
                    text = "総距離"
                    visibility = View.GONE
                }
                textView {
                    id = totalDistanceTextViewId
                    textColor = Color.BLACK
                    text = "??? メートル"
                    visibility = View.GONE
                }
                textView {
                    textColor = Color.BLACK
                    text = "スコア"
                    textSize = 21f
                }
                textView {
                    id = dailyScoreTextViewId
                    textColor = Color.BLACK
                    textSize = 21f
                }
            }


            lparams(width = matchParent, height = matchParent)
        }
    }
}