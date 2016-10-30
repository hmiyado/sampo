package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewManager
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.android.withContext
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import org.jetbrains.anko.custom.ankoView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.*

/**
 * Created by hmiyado on 2016/09/23.
 */

// settings for anko custom view
inline fun ViewManager.mapView(theme: Int = 0) = mapView(theme) {}
inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView({ MapView(it) }, theme, init)

class MapView(context: Context): View(context), LazyKodeinAware {
    override val kodein = LazyKodein(appKodein)
    val paint: Paint = Paint()
    val UseLocation: UseLocation by kodein.instance<UseLocation>()
    var location: Location? = null

    init {
        createLocationObservable().subscribe()
        UseLocation.startLocationObserve()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return
        location ?: return

        canvas.drawText("Location¥n", 100f, 100f, paint)
        canvas.drawText(location.toString(), 100f, 100f, paint)
    }

    private fun createLocationObservable(): Observable<Location> {
        return UseLocation
                .getLocationObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    location = it
                    Timber.d("subscribing: ${location}")
                    invalidate()
                }
                .onErrorResumeNext {
                    Timber.e(it, "error on get location")
                    createLocationObservable()
                }
    }
}