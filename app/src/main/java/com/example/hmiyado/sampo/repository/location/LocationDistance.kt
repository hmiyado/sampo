package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.math.HubenyFormula
import com.example.hmiyado.sampo.domain.model.Location

/**
 * Created by hmiyado on 2016/09/22.
 * 位置情報をもとに距離計算をするインターフェース
 * 現状はとりあえずヒュベニの公式以外の実装がないので，直接書いてしまっている
 * ヒュベニの公式じゃいかんってことになったら，実装を注入する方式に変更する
 */
interface LocationDistance {
    fun determineDistance(p1: Location, p2: Location): Double {
        return HubenyFormula.determineDistance(p1, p2)
    }
}