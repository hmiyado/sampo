package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/11/03.
 * ここにはDoubleなどの拡張関数をおく
 */

/**
 * 実装は |d| < Double.MIN_VALUE ならtrue
 * @param d ゼロ判定する値
 * @return ゼロならtrue
 */
fun Double.isZero(): Boolean = Math.abs(this) < Double.MIN_VALUE

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
fun Double.toDegree(): Double = 360 * this / (2 * Math.PI)