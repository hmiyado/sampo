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
import com.hmiyado.sampo.usecase.map.interaction.UpdateValidityPeriodEnd
import com.hmiyado.sampo.usecase.map.store.MapStore
import com.hmiyado.sampo.usecase.map.store.MapStoreImpl
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/03/23.
 */
val resultMenuUseCaseModule = Module {
    //    bind<CalculateTotalDistance>() with singleton { CalculateTotalDistance(instance(), instance(), instance()) }
    bind<SelectResultMenuItem<*>>() with singleton { SelectResultMenuItem<ResultMenuItem>(instance(), instance()) }
    bind<List<Interaction<*>>>() with singleton { listOf(instance<SelectResultMenuItem<*>>()) }
}

enum class ResultSummaryTag {
    DailyScoreView,
    WeeklyScoreView
}

val resultSummaryUseCaseModule = Module {
    bind<MapStore>() with singleton { MapStoreImpl(instance()) }

    bind<LoadTerritories>() with singleton { LoadTerritories(instance(), instance()) }
    bind<UpdateLocation>() with singleton { UpdateLocation(instance(), instance()) }
    bind<UpdateTerritory>() with singleton { UpdateTerritory(instance()) }
    bind<UpdateValidityPeriodEnd>() with singleton { UpdateValidityPeriodEnd(instance()) }
    bind<DisplayScore>(ResultSummaryTag.DailyScoreView) with singleton { DisplayScore(instance(), instance(), Period.ofDays(1), instance(ResultSummaryTag.DailyScoreView)) }
    bind<DisplayScore>(ResultSummaryTag.WeeklyScoreView) with singleton { DisplayScore(instance(), instance(), Period.ofDays(7), instance(ResultSummaryTag.WeeklyScoreView)) }
    bind<ViewVisitedArea>() with singleton { ViewVisitedArea(instance(), instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<DisplayScore>(ResultSummaryTag.DailyScoreView),
                instance<DisplayScore>(ResultSummaryTag.WeeklyScoreView),
                instance<LoadTerritories>(),
                instance<UpdateLocation>(),
                instance<UpdateTerritory>(),
                instance<UpdateValidityPeriodEnd>(),
                instance<ViewVisitedArea>()
        )
    }
}