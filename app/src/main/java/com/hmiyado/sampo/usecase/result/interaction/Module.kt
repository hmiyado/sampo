package com.hmiyado.sampo.usecase.result.interaction

import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.hmiyado.sampo.domain.result.ResultMenuItem
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.interaction.DisplayScore
import com.hmiyado.sampo.usecase.map.interaction.UpdateLocation
import com.hmiyado.sampo.usecase.map.interaction.UpdateTerritory
import com.hmiyado.sampo.usecase.map.store.MapStore
import com.hmiyado.sampo.usecase.map.store.MapStoreImpl

/**
 * Created by hmiyado on 2017/03/23.
 */
val resultMenuUseCaseModule = Module {
    //    bind<CalculateTotalDistance>() with singleton { CalculateTotalDistance(instance(), instance(), instance()) }
    bind<SelectResultMenuItem<*>>() with singleton { SelectResultMenuItem<ResultMenuItem>(instance(), instance()) }
    bind<List<Interaction<*>>>() with singleton { listOf(instance<SelectResultMenuItem<*>>()) }
}

val resultSummaryUseCaseModule = Module {
    bind<MapStore>() with singleton { MapStoreImpl(instance()) }

    bind<LoadLocation>() with singleton { LoadLocation(instance(), instance()) }
    bind<UpdateLocation>() with singleton { UpdateLocation(instance(), instance()) }
    bind<UpdateTerritory>() with singleton { UpdateTerritory(instance()) }
    bind<DisplayScore>() with singleton { DisplayScore(instance(), instance(), instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<DisplayScore>(),
                instance<LoadLocation>(),
                instance<UpdateLocation>(),
                instance<UpdateTerritory>()
        )
    }
}