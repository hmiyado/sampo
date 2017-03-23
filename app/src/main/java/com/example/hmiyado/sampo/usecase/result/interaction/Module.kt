package com.example.hmiyado.sampo.usecase.result.interaction

import com.example.hmiyado.sampo.usecase.Interaction
import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by hmiyado on 2017/03/23.
 */
val resultMenuUseCaseModule = Module {
    //    bind<CalculateTotalDistance>() with singleton { CalculateTotalDistance(instance(), instance(), instance()) }
    bind<SelectResultMenuItem>() with singleton { SelectResultMenuItem(instance(), instance()) }
    bind<List<Interaction<*>>>() with singleton { listOf(instance<SelectResultMenuItem>()) }
}