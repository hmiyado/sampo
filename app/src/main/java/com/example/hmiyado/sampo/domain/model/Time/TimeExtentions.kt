package com.example.hmiyado.sampo.domain.model.Time

import org.threeten.bp.Instant
import java.util.*

/**
 * Created by hmiyado on 2017/02/23.
 */

fun Instant.toDate(): Date = Date(this.toEpochMilli())

fun Date.toInstant(): Instant = Instant.ofEpochMilli(this.time)