package com.example.hmiyado.sampo.domain.math.function

import com.example.hmiyado.sampo.domain.math.square

/**
 * Created by hmiyado on 2017/03/29.
 *
 * 天井付きの比例関数．
 * y = x + a / L と y = ceil に漸近する関数．
 * 2直線で区切られる4つの領域のうち，右下に位置する．
 * 原点を通る
 *
 * @param ceil 天井．上限
 * @param ratio 増加率．天井付近に至るまでの速さ．高いほど，さっさと天井にたどり着く
 */
class CeiledProportionalFunction(
        ceil: Double = 100.0,
        ratio: Double = 1.0
) {
    private val a = ratio
    private val L = ceil

    fun invoke(x: Double): Double {
        // x = y - a/L - a / (y - L)
        // この式を y について解き直したもの
        // https://www.desmos.com/calculator
        return ((x + L + a / L) - Math.sqrt((x + L + a / L).square() - 4 * L * x)) / 2
    }
}