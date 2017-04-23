package com.hmiyado.sampo.controller.result

import android.widget.TextView
import com.hmiyado.sampo.domain.model.Area
import com.hmiyado.sampo.usecase.result.UseVisitedAreaViewer

/**
 * Created by hmiyado on 2017/04/23.
 */
class VisitedAreaViewController(
        private val textView: TextView
) : UseVisitedAreaViewer.Sink {
    private val allAreaNum = Area.DIVISION_NUMBER.toLong() * Area.DIVISION_NUMBER.toLong()
    override fun view(areas: Set<Area>) {
        textView.text = "${areas.size} / $allAreaNum"
    }

}