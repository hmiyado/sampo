package com.example.hmiyado.sampo.view.custom

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.view.ViewManager
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.toDegree
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


// settings for anko custom view
inline fun ViewManager.mapView(theme: Int = 0) = mapView(theme) {}
inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView({ MapView(it) }, theme, init)

/**
 * Created by hmiyado on 2016/09/23.
 * 位置情報を描画する
 * ピンチによる拡大縮小・回転　に対応する
 */
class MapView(context: Context): View(context), LazyKodeinAware {
    override val kodein = LazyKodein(appKodein)
    private val paintMapPoint: Paint = Paint()
    private val UseLocation: UseLocation by kodein.instance<UseLocation>()
    private var location: Location? = null
    private var scaleFactor: Float = 1.0f
    private var rotateAngleDegree: Float = 0f

    /**
     * 地図の縮尺．
     * 100 px あたりの地図上の距離(メートル)を表す．
     */
    private var mapScale: Float = 100f
        get() = 100f * scaleFactor

    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, createGestureDetector())

    init {
        settingPaintMapPoint()
        createLocationObservable().subscribe()
        UseLocation.startLocationObserve()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return
        location ?: return

        // デバッグ用位置情報出力
        canvas.drawText(location.toString(), 0f, canvas.height - 100f, paintMapPoint)
        // デバッグ用縮尺出力
        canvas.drawLine(50f, canvas.height - 50f, 150f, canvas.height - 50f, paintMapPoint) // 縮尺定規
        canvas.drawText("$mapScale [m]", 250f, canvas.height - 50f, paintMapPoint) // 縮尺倍率


        // canvasの中心を画面の中心に移動する
        canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat() )

        // canvas を回転する
        canvas.rotate(rotateAngleDegree)

        canvas.drawLine(-600f, 0f, 600f, 0f, paintMapPoint)
        canvas.drawLine(0f, -1000f, 0f, 1000f, paintMapPoint)

        canvas.drawRect(-100f,200f,300f,400f, paintMapPoint)

        // 位置情報出力
        canvas.drawCircle(-500f, 0f, 75f, paintMapPoint)

    }

    private fun createLocationObservable(): Observable<Location> {
        return UseLocation
                .getLocationObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    location = it
//                    Timber.d("subscribing: ${location}")
                    invalidate()
                }
                .onErrorResumeNext {
                    Timber.e(it, "error on get location")
                    createLocationObservable()
                }
    }

    private fun settingPaintMapPoint() {
        paintMapPoint.color = Color.BLUE
        paintMapPoint.isAntiAlias = true
        paintMapPoint.strokeWidth = 20f
    }

    private fun createGestureDetector(): OnScaleGestureListener {
        return object : OnScaleGestureListener {
            override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(p0: ScaleGestureDetector?) {
                Timber.d("onScaleEnd")
                return
            }

            override fun onScale(p0: ScaleGestureDetector?): Boolean {
                p0 ?: return true

                // 倍率
                scaleFactor *= p0.scaleFactor

//                Timber.d("scale: $scaleFactor")

                //invalidate()

                return true
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        rotateAngleDegree += determineRotateAngleDegree(event)
        Timber.d("rotate angle degree = $rotateAngleDegree")
        return scaleGestureDetector.onTouchEvent(event)
    }

    private var point1: PointF = PointF(0f,0f)
    private var point2: PointF = PointF(0f,0f)
    private var time: Long = 0

    private fun determineRotateAngleDegree(event: MotionEvent): Float {
        if (event.pointerCount != 2) {
            // ちょうど２点タッチしていなければ，回転を検知することはできない
            return 0f
        }
        if (event.eventTime - time > 100) {
            // イベント間の時間は１００ｍｓ空いていたなら，それは新たなタッチイベントだと考えられる
            // envet.action == MotionEvent.ACTION_DOWN はタッチ点が２点以外のときも発生し，上のif文でリジェクトされてしまう
            // そのため，イベント種別ではなく時間をみて判断する
            point1 = PointF(event.getX(0), event.getY(0))
            point2 = PointF(event.getX(1), event.getY(1))
        }
        time = event.eventTime

        val nextPoint1 = PointF(event.getX(0), event.getY(0))
        val nextPoint2 = PointF(event.getX(1), event.getY(1))

        val angle = Geometry.determineAngle(point1, point2, nextPoint1, nextPoint2).toDegree()

        point1 = nextPoint1
        point2 = nextPoint2

        return angle
    }
}