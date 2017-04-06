package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.Degree

data class DrawableMap(
        val originalLocation: Location,
        val scaleFactor: Float,
        val rotateAngle: Degree
)