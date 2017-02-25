package com.example.hmiyado.sampo.controller

import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.usecase.map.compassview.UseCompassViewOutput
import com.example.hmiyado.sampo.view.map.custom.CompassView
import rx.Observable
import rx.Subscription

/**
 * Created by hmiyado on 2016/12/21.
 *
 * CompassView のController
 */
class CompassViewController(
        compassView: CompassView
) : ViewController<CompassView>(compassView), UseCompassViewOutput {

    fun drawCompass(canvas: Canvas, orientation: Orientation) {
        val bitmap = BitmapFactory.decodeResource(view.resources, android.R.drawable.arrow_up_float)
        val centerX = view.width / 2f
        val centerY = view.height / 2f
        canvas.rotate(orientation.azimuth.toFloat(), centerX, centerY)
        canvas.drawBitmap(bitmap, centerX, centerY, null)
    }

    override fun setOnOrientationSignal(orientationSignal: Observable<Orientation>): Subscription =
            orientationSignal
                    .doOnNext { invalidate() }
                    .bindToCompassView()
                    .subscribe()

    override fun setOnDrawSignal(orientationSignal: Observable<Orientation>, onDrawSignal: Observable<Canvas>): Subscription =
            onDrawSignal
                    .withLatestFrom(orientationSignal, { canvas, orientation -> Pair(canvas, orientation) })
                    .doOnNext {
                        val canvas = it.first
                        val orientation = it.second

                        drawCompass(canvas, orientation)

                        //                        Timber.d(it.second.toString())
                    }
                    .bindToCompassView()
                    .subscribe()


    private fun <T> rx.Observable<T>.bindToCompassView() = bindToViewLifecycle(this)

}