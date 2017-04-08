package com.example.hmiyado.sampo.view.result.repository.marker

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.view.result.customview.ResultMarkerView
import java.util.*

/**
 * Created by hmiyado on 2017/02/18.
 */
class ResultMarkerListAdapter(val context: Context) : BaseAdapter() {
    val markers = ArrayList<Marker>()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        if (p1 != null) {
            p1 as ResultMarkerView
            p1.set(getItem(p0) as Marker)
            return p1
        }
        p2 ?: return View(context)
        val view = ResultMarkerView(context)
        view.set(getItem(p0) as Marker)
        return view
    }

    override fun getItem(p0: Int): Any {
        return markers[p0]
    }

    override fun getItemId(p0: Int): Long {
        return markers[p0].hashCode().toLong()
    }

    override fun getCount(): Int {
        return markers.count()
    }
}