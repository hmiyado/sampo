package com.hmiyado.sampo.usecase.result.interaction

import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.interaction.DisplayScore

/**
 * Created by hmiyado on 2017/03/23.
 */
val resultMenuUseCaseModule = Module {
    //    bind<CalculateTotalDistance>() with singleton { CalculateTotalDistance(instance(), instance(), instance()) }
    bind<SelectResultMenuItem>() with singleton { SelectResultMenuItem(instance(), instance()) }
    bind<List<Interaction<*>>>() with singleton { listOf(instance<SelectResultMenuItem>()) }
}

val resultSummaryUseCaseModule = Module {
    bind<DisplayScore>() with singleton { DisplayScore(instance(), instance(), instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<DisplayScore>()
        )
    }
}