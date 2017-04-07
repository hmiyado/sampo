package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Measurement

data class DrawableMap(
        val originalLocation: Location,
        val scaleFactor: Float,
        val rotateAngle: Degree,
        val measurement: Measurement
)