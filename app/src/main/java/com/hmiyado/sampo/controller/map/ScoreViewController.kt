package com.hmiyado.sampo.controller.map

import android.widget.TextView
import com.hmiyado.sampo.controller.ViewController
import com.hmiyado.sampo.usecase.map.UseScoreView

/**
 * Created by hmiyado on 2017/04/08.
 */
class ScoreViewController(view: TextView) : ViewController<TextView>(view), UseScoreView.Sink {
    override fun setScore(score: Double) {
        view.text = String.format("%,3d", score.toInt())
    }
}