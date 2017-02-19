package com.example.hmiyado.sampo.view.result.customview

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.domain.model.Location
import org.jetbrains.anko.find

/**
 * Created by hmiyado on 2017/02/18.
 */
class ResultRealmItemView(context: Context) : LinearLayout(context) {
    val timeView: TextView
    val longitudeView: TextView
    val latitudeView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.result_realm_item_layout, this)
        timeView = find<TextView>(R.id.result_realm_item_time)
        longitudeView = find<TextView>(R.id.result_realm_item_longitude)
        latitudeView = find<TextView>(R.id.result_realm_item_latitude)
    }

    fun setLocation(location: Location) {
        timeView.text = location.localDateTime.toString()
        longitudeView.text = location.longitude.toString()
        latitudeView.text = location.latitude.toString()
    }
}