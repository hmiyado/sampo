package com.hmiyado.sampo.view.result.ui

import android.view.View
import com.hmiyado.sampo.R
import com.hmiyado.sampo.view.common.sampoToolbar
import com.trello.rxlifecycle2.components.RxFragment
import org.jetbrains.anko.*

/**
 * Created by hmiyado on 2017/03/01.
 */
class ResultSummaryFragmentUi : AnkoComponent<RxFragment> {
    val totalDistanceTextViewId = View.generateViewId()
    val dailyScoreTextViewId = View.generateViewId()
    val weeklyScoreTextViewId = View.generateViewId()
    val areaScoreTextViewId = View.generateViewId()

    override fun createView(ui: AnkoContext<RxFragment>): View = with(ui) {
        val theme = R.style.AppTheme
        verticalLayout(theme = theme) {
            sampoToolbar { }
            verticalLayout {
                textView {
                    text = "総距離"
                    visibility = View.GONE
                }
                textView {
                    id = totalDistanceTextViewId
                    text = "??? メートル"
                    visibility = View.GONE
                }
                textView(theme) {
                    text = "今日のなわばり"
                    textSize = 21f
                }
                textView(theme) {
                    id = dailyScoreTextViewId
                    textSize = 21f
                }
                textView(theme) {
                    text = "今週のなわばり"
                    textSize = 21f
                }
                textView(theme) {
                    id = weeklyScoreTextViewId
                    textSize = 21f
                }
                textView(theme) {
                    text = "いままでのなわばり"
                    textSize = 21f
                }
                textView(theme) {
                    id = areaScoreTextViewId
                    textSize = 21f
                }
            }


            lparams(width = matchParent, height = matchParent)
        }
    }
}