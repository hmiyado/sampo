package com.example.hmiyado.sampo.view.result

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.view.result.customview.ResultRealmItemView
import java.util.*

/**
 * Created by hmiyado on 2017/02/18.
 */
class ResultRepositoryItemListAdapter(val context: Context) : BaseAdapter() {
    val locations = ArrayList<Location>()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        if (p1 != null) {
            p1 as ResultRealmItemView
            p1.setLocation(getItem(p0) as Location)
            return p1
        }
        p2 ?: return View(context)
        val view = ResultRealmItemView(context)
        view.setLocation(getItem(p0) as Location)
        return view
    }

    override fun getItem(p0: Int): Any {
        return locations[p0]
    }

    override fun getItemId(p0: Int): Long {
        return locations[p0].localDateTime.toUnixTime().toLong()
    }

    override fun getCount(): Int {
        return locations.count()
    }
}