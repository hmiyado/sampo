package com.example.hmiyado.sampo.presenter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.example.hmiyado.sampo.domain.math.Geometry
import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.view.custom.MapView
import com.github.salomonbrys.kodein.instance
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/04.
 * {@link MapView }に対応するPresenter
 */
class MapViewPresenter(
        private val mapView: MapView
) {

    private val UseLocation: UseLocation by mapView.injector.instance()

    private val paintMapPoint: Paint = Paint()

    /**
     * 倍率１のときの，100 px あたりの地図上の距離（メートル）
     */
    private val SCALE_UNIT = 100f

    private var location: Location? = null
    private var scaleFactor: Float = 1.0f
    private var rotateAngleDegree: Float = 0f

    /**
     * 地図の縮尺．
     * 100 px あたりの地図上の距離(メートル)を表す．
     */
    private val mapScale: Float
        get() = SCALE_UNIT * scaleFactor

    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(mapView.context, createGestureDetector())

    private var point1: PointF = PointF(0f, 0f)
    private var point2: PointF = PointF(0f, 0f)
    private var time: Long = 0

    init {
        settingPaintMapPoint()
        createLocationObservable().subscribe()
        getOnTouchEventSignal().subscribe()
        UseLocation.startLocationObserve()
    }

    private fun createLocationObservable(): Observable<Location> {
        return UseLocation
                .getLocationObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    location = it
//                    Timber.d("subscribing: ${location}")
                    mapView.invalidate()
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

    private fun createGestureDetector(): ScaleGestureDetector.OnScaleGestureListener {
        return object : ScaleGestureDetector.OnScaleGestureListener {
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

    private fun getOnTouchEventSignal(): Observable<MotionEvent> {
        return mapView.getOnTouchSignal()
                .doOnNext {
                    rotateAngleDegree += determineRotateAngleDegree(it)
                    Timber.d("rotate angle degree = $rotateAngleDegree")
                }
                .doOnNext {
                    scaleGestureDetector.onTouchEvent(it)
                }
    }

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


    fun onDraw(canvas: Canvas) {
        // デバッグ用位置情報出力
        canvas.drawText(location.toString(), 0f, canvas.height - 100f, paintMapPoint)
        // デバッグ用縮尺出力
        canvas.drawLine(50f, canvas.height - 50f, 150f, canvas.height - 50f, paintMapPoint) // 縮尺定規
        canvas.drawText("$mapScale [m]", 250f, canvas.height - 50f, paintMapPoint) // 縮尺倍率


        // canvasの中心を画面の中心に移動する
        canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())

        // canvas を回転する
        canvas.rotate(rotateAngleDegree)

        canvas.drawLine(-600f, 0f, 600f, 0f, paintMapPoint)
        canvas.drawLine(0f, -1000f, 0f, 1000f, paintMapPoint)

        canvas.drawRect(-100f, 200f, 300f, 400f, paintMapPoint)

        // 位置情報出力
        canvas.drawCircle(-500f, 0f, 75f, paintMapPoint)

    }
}