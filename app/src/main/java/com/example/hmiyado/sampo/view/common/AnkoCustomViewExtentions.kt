package com.example.hmiyado.sampo.view.common

import com.example.hmiyado.sampo.R
import org.jetbrains.anko.custom.ankoView

inline fun android.view.ViewManager.sampoToolbar(
        theme: Int = R.style.AppTheme,
        init: SampoToolbar.() -> Unit) = ankoView(::SampoToolbar, theme, init)
