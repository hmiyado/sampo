package com.example.hmiyado.sampo.domain.model

import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/15.
 *
 * 地図の情報のモデル．
 *
 * @param originalLocation 地図の原点．普通はユーザーの位置？
 * @param orientation ユーザーの向いている方向
 * @param scaleFactor 地図の縮尺倍率
 * @param rotateAngle 地図が北からどれだけ回転して表示されているかを表す回転角
 */
class Map(
        val originalLocation: Location,
        val orientation: Orientation,
        val scaleFactor: Float,
        val rotateAngle: Float

) {
    companion object {
        fun empty(): Map {
            return Map(Location.empty(), Orientation.empty(), 0f, 0f)
        }
    }

    /**
     * 地図の縮尺
     */
    val scale: Float
        get() = scaleFactor * SCALE_UNIT

    /**
     * 倍率１のときの，100 px あたりの地図上の距離（メートル）
     */
    val SCALE_UNIT = 100

    override fun toString(): String {
        return "Map(originalLocation=$originalLocation), orientation=$orientation, scaleFactor=$scaleFactor, rotateAngle=$rotateAngle"
    }

    class Builder(map: Map) {
        private var originalLocation = map.originalLocation
        private var orientation = map.orientation
        private var scaleFactor = map.scaleFactor
        private var rotateAngle = map.rotateAngle

        fun build(): Map {
            return Map(
                    originalLocation,
                    orientation,
                    scaleFactor,
                    rotateAngle
            )
        }

        fun setOriginalLocation(originalLocation: Location): Builder {
            this.originalLocation = originalLocation
            return this
        }

        fun setOrientation(orientation: Orientation): Builder {
            this.orientation = orientation
            return this
        }

        fun setScaleFactor(scaleFactor: Float): Builder {
            this.scaleFactor = scaleFactor
            return this
        }

        fun setRotateAngle(rotateAngle: Float): Builder {
            this.rotateAngle = rotateAngle
            Timber.d("rotateAngle(this; arg)=(${this.rotateAngle}; $rotateAngle)")
            return this
        }
    }
}