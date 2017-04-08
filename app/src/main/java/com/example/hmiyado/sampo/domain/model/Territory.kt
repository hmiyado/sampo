package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/03/21.
 */
class Territory(
        val area: Area = Area(),
        val locations: List<Location> = emptyList()
) : Comparable<Territory> {

    /**
     * 新しい位置情報を加えた縄張り情報を返す
     */
    fun addLocation(location: Location): Territory {
        return Territory(area, locations.plus(location))
    }


    override fun compareTo(other: Territory): Int {
        if (this == other) {
            return 0
        }

        return area.compareTo(other.area)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Territory

        if (area != other.area) return false

        return true
    }

    override fun hashCode(): Int {
        var result = area.hashCode()
        result = 31 * result + locations.hashCode()
        return result
    }

    override fun toString(): String {
        return "Territory(area=$area, markers=$locations)"
    }
}