package com.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/11/03.
 * ここにはDoubleなどの拡張関数をおく
 */

/**
 * Double や Float の有効桁数．何の根拠もなく恣意的に決めた値なので，適宜変更すべし
 */
val EPSILON = 1e-10

/**
 * @return |d| < Double.MIN_VALUE ならtrue
 */
fun Float.isZero(): Boolean = Math.abs(this) < EPSILON

fun Double.isZero(): Boolean = Math.abs(this) < EPSILON

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
fun Double.toDegree(): Degree = Degree(360 * this / (2 * Math.PI))

fun Double.toRadian(): Radian = Radian(this)

operator fun Int.times(radian: Radian) = radian * this.toDouble()

operator fun Double.times(radian: Radian) = radian * this

fun cos(radian: Radian): Double = Math.cos(radian.toDouble())

fun sin(radian: Radian): Double = Math.sin(radian.toDouble())

fun asin(x: Double) = Math.asin(x).toRadian()

fun acos(x: Double): Radian = Math.acos(x).toRadian()

fun atan2(y: Double, x: Double): Radian = Math.atan2(y, x).toRadian()

fun abs(radian: Radian) = Radian(Math.abs(radian.toDouble()))