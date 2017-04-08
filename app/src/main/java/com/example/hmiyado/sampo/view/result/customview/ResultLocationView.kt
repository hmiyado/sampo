package com.example.hmiyado.sampo.view.result.customview

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hmiyado.sampo.R
import com.example.hmiyado.sampo.domain.model.Location
import org.jetbrains.anko.find
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by hmiyado on 2017/02/18.
 */
class ResultLocationView(context: Context) : LinearLayout(context) {
    val timeView: TextView
    val longitudeView: TextView
    val latitudeView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.result_location_layout, this)
        timeView = find<TextView>(R.id.result_location_time)
        longitudeView = find<TextView>(R.id.result_location_longitude)
        latitudeView = find<TextView>(R.id.result_location_latitude)
    }

    fun setLocation(location: Location) {
        setLocalDateTime(location.timeStamp)
        longitudeView.text = location.longitude.toString()
        latitudeView.text = location.latitude.toString()
    }

    private fun setLocalDateTime(timeStamp: Instant) {

        val formattedString = timeStamp.atOffset(ZoneOffset.ofHours(9)).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
        timeView.text = formattedString
    }
}