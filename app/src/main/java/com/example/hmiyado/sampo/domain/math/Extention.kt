package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/11/03.
 * ここにはDoubleなどの拡張関数をおく
 */

/**
 * @return |d| < Double.MIN_VALUE ならtrue
 */
fun Float.isZero(): Boolean = Math.abs(this) < Float.MIN_VALUE

/**
 * @return 2乗
 */
fun Double.square(): Double = this * this

/**
 * @return 3乗
 */
fun Double.cube(): Double = this * this * this

/**
 * 弧度法(radian) から度数法(degree) に変換する
 * @return 変換後の値
 */
fun Float.toDegree(): Float = (360 * this / (2 * Math.PI)).toFloat()