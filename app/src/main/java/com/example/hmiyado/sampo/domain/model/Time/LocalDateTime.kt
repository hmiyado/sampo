package com.example.hmiyado.sampo.domain.model.Time

import com.example.hmiyado.sampo.domain.exception.IllegalTypeOfTimeException
import com.example.hmiyado.sampo.domain.model.Time.Month.Month
import com.example.hmiyado.sampo.domain.model.Time.Year.Year

/**
 * Created by hmiyado on 2016/07/29.
 */
class LocalDateTime protected constructor(
        val year: Year,
        val month: Month,
        val day: Day,
        val hour: Hour,
        val minute: Minute,
        val second: Second
) : Comparable<LocalDateTime> {
    companion object {
        val UnixEpoch = Factory.init().complete()

        fun empty(): LocalDateTime = LocalDateTime(Year(-1), Month.newInstance(0, Year(-1)), Day(0), Hour(0), Minute(0), Second(0))

        object Factory {
            private var year: Year = Year(1970)
            private var month: Month = Month.newInstance(1, year)
            private var day: Day = Day(1)
            private var hour = Hour(0)
            private var minute = Minute(0)
            private var second = Second(0)

            private fun getUpperThresholdInclusive(time: Time): Int {
                when (time) {
                    is Day               -> return month.LastDay
                    is Hour              -> return 23
                    is Minute, is Second -> return 59
                    else                 -> throw IllegalTypeOfTimeException()
                }
            }

            private fun getLowerThresholdInclusive(time: Time): Int {
                when (time) {
                    is Day                        -> return 1
                    is Hour, is Minute, is Second -> return 0
                    else                          -> throw IllegalTypeOfTimeException()
                }
            }

            fun initByUnixTime(second: Second): Factory {
                init(UnixEpoch)
                build(second)
                return this
            }

            fun init(): Factory {
                year = Year(1970)
                month = Month.newInstance(1, year)
                day = Day(1)
                hour = Hour(0)
                minute = Minute(0)
                second = Second(0)
                return this
            }

            fun init(localDateTime: LocalDateTime): Factory {
                year = localDateTime.year
                month = localDateTime.month
                day = localDateTime.day
                hour = localDateTime.hour
                minute = localDateTime.minute
                second = localDateTime.second
                return this
            }

            fun build(year: Year): Factory {
                return build(Month.Companion.newInstance(month, year))
            }

            fun build(month: Month): Factory {
                this.month = month
                this.year = month.year
                return this
            }

            fun build(day: Day): Factory {
                var daysOfThisMonth = Day(month.LastDay)
                when {
                    day > daysOfThisMonth                      -> {
                        var newDay: Day = day
                        do {
                            newDay -= daysOfThisMonth
                            this.month += Month.Interval(1)
                            daysOfThisMonth = Day(this.month.LastDay)
                        } while (newDay > daysOfThisMonth)
                        build(this.month)
                        build(newDay)
                    }
                    day <= Day(month.LastDay) && day >= Day(1) -> {
                        this.day = day
                    }
                    else                                       -> {
                        var newDay: Day = day
                        do {
                            newDay += daysOfThisMonth
                            this.month -= Month.Interval(1)
                            daysOfThisMonth = Day(this.month.LastDay)
                        } while (newDay < Day(1))
                        build(this.month)
                        build(newDay)
                    }
                }

                return this
            }

            fun build(hour: Hour): Factory {
                return build(
                        hour,
                        { it: Int -> build(day + Day(it)) },
                        { it: Int -> build(day - Day(it)) },
                        { it: Int -> this.hour = Hour(it) }
                )
            }

            fun build(minute: Minute): Factory {
                return build(
                        minute,
                        { it: Int -> build(hour + Hour(it)) },
                        { it: Int -> build(hour - Hour(it)) },
                        { it: Int -> this.minute = Minute(it) }
                )

            }

            fun build(second: Second): Factory {
                return build(
                        second,
                        { it: Int -> build(minute + Minute(it)) },
                        { it: Int -> build(minute - Minute(it)) },
                        { it: Int -> this.second = Second(it) }
                )
            }

            private fun build(
                    time: Time,
                    plusBuilder: (moveUp: Int) -> Unit,
                    minusBuilder: (moveDown: Int) -> Unit,
                    assigner: (timeValue: Int) -> Unit
            ): Factory {
                val timeInt = time.toInt()
                val lower = getLowerThresholdInclusive(time)
                val upper = getUpperThresholdInclusive(time)
                when {
                    timeInt > upper                      -> {
                        val moveUp = timeInt / (upper + 1)
                        val residueTime = timeInt % (upper + 1)
                        plusBuilder(moveUp)
                        assigner(residueTime)
                    }
                    timeInt <= upper && timeInt >= lower -> {
                        assigner(timeInt)
                    }
                    else                                 -> {
                        val moveDown = Math.abs(timeInt) / (upper + 1) + 1
                        val residueTime = timeInt % (upper + 1) + upper + 1 + lower
                        minusBuilder(moveDown)
                        assigner(residueTime)
                    }
                }
                return this
            }

            fun complete(): LocalDateTime {
                return LocalDateTime(year, month, day, hour, minute, second)
            }
        }
    }

    fun toUnixTime(): Second {
        val yearSeconds = UnixEpoch.year.pastSecondsUntil(year)
        val monthSeconds = UnixEpoch.month.pastSecondsUntil(month)
        val daySeconds = day.toSecond() - Day(1).toSecond()
        val hourSeconds = hour.toSecond()
        val minuteSeconds = minute.toSecond()
        val seconds = second
        return yearSeconds + monthSeconds + daySeconds + hourSeconds + minuteSeconds + seconds
    }

    fun getMatchProperty(time: Time): Time {
        when (time) {
            is Second -> return second
            is Minute -> return minute
            is Hour   -> return hour
            is Day    -> return day
            else      -> throw IllegalTypeOfTimeException()
        }
    }

    operator fun plus(time: Time): LocalDateTime {
        when (time) {
            is Second -> return Factory.init(this).build(time + second).complete()
            is Minute -> return Factory.init(this).build(time + minute).complete()
            is Hour   -> return Factory.init(this).build(time + hour).complete()
            is Day    -> return Factory.init(this).build(time + day).complete()
            else      -> throw IllegalTypeOfTimeException()
        }
    }

    operator fun minus(time: Time): LocalDateTime {
        when (time) {
            is Second -> return Factory.init(this).build(second - time).complete()
            is Minute -> return Factory.init(this).build(minute - time).complete()
            is Hour   -> return Factory.init(this).build(hour - time).complete()
            is Day    -> return Factory.init(this).build(day - time).complete()
            else      -> throw IllegalTypeOfTimeException()
        }
    }

    operator fun plus(monthInterval: Month.Interval): LocalDateTime {
        return Factory.init(this).build(month + monthInterval).complete()
    }

    operator fun minus(monthInterval: Month.Interval): LocalDateTime {
        return Factory.init(this).build(month - monthInterval).complete()
    }

    operator fun plus(yearInterval: Year.Interval): LocalDateTime {
        return Factory.init(this).build(year + yearInterval).complete()
    }

    operator fun minus(yearInterval: Year.Interval): LocalDateTime {
        return Factory.init(this).build(year - yearInterval).complete()
    }

    // LocalDateTime 同士を足すことに意味は無い
    //    operator fun plus(other: LocalDateTime): LocalDateTime

    operator fun minus(localDateTime: LocalDateTime): Second {
        return toUnixTime() - localDateTime.toUnixTime()
    }

    override operator fun compareTo(other: LocalDateTime): Int {
        when {
            year != other.year     -> return (year - other.year).value
            month != other.month   -> return (month - other.month).value
            day != other.day       -> return (day - other.day).toInt()
            hour != other.hour     -> return (hour - other.hour).toInt()
            minute != other.minute -> return (minute - other.minute).toInt()
            second != other.second -> return (second - other.second).toInt()
            else                   -> return 0
        }
    }


    override fun toString(): String {
        val strings = arrayListOf(
                year.toString(),
                month.toString(),
                day.toString(),
                hour.toString(),
                minute.toString(),
                second.toString()
        )
        return "LocalDateTime(${strings.joinToString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as LocalDateTime

        if (year != other.year) return false
        if (month != other.month) return false
        if (day != other.day) return false
        if (hour != other.hour) return false
        if (minute != other.minute) return false
        if (second != other.second) return false

        return true
    }

    override fun hashCode(): Int {
        var result = year.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + day.hashCode()
        result = 31 * result + hour.hashCode()
        result = 31 * result + minute.hashCode()
        result = 31 * result + second.hashCode()
        return result
    }

}
