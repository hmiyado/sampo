package com.example.hmiyado.sampo.view.result.customview

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.domain.model.Marker
import org.jetbrains.anko.find
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by hmiyado on 2017/02/18.
 */
class ResultMarkerView(context: Context) : LinearLayout(context) {
    val beginTime: TextView
    val endTime: TextView
    val longitudeView: TextView
    val latitudeView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.result_marker_layout, this)
        beginTime = find<TextView>(R.id.result_marker_begin_time)
        endTime = find<TextView>(R.id.result_marker_end_time)
        longitudeView = find<TextView>(R.id.result_marker_longitude)
        latitudeView = find<TextView>(R.id.result_marker_latitude)
    }

    fun set(marker: Marker) {
        setLocalDateTime(beginTime, marker.validityPeriod.begin)
        setLocalDateTime(endTime, marker.validityPeriod.end)
        longitudeView.text = marker.location.longitude.toString()
        latitudeView.text = marker.location.latitude.toString()
    }

    private fun setLocalDateTime(textView: TextView, timeStamp: Instant) {

        val formattedString = timeStamp.atOffset(ZoneOffset.ofHours(9)).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
        textView.text = formattedString
    }
}