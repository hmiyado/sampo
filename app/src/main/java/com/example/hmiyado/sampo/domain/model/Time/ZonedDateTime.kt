package com.example.hmiyado.sampo.domain.model.Time

/**
 * Created by hmiyado on 2016/07/29.
 */
class ZonedDateTime(
        val localDateTime: LocalDateTime,
        val timeZone: TimeZone
) {
    companion object {
        fun empty(): ZonedDateTime = ZonedDateTime(LocalDateTime.empty(), TimeZone.empty())
    }

    fun isEmpty(): Boolean {
        return timeZone.isEmpty()
    }

    fun convertToUtcLocalDateTime(): LocalDateTime {
        val utcLocalDateTime = LocalDateTime.Companion.Factory
                .init(localDateTime)
                .build(localDateTime.hour + Hour((if(timeZone.isPlus) 1 else -1) * timeZone.hour.toInt()) )
                .build(localDateTime.minute + Minute((if(timeZone.isPlus) 1 else -1) * timeZone.minute.toInt()) )
                .complete()
        return utcLocalDateTime
    }
}
