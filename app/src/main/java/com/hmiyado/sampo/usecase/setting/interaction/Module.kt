package com.hmiyado.sampo.usecase.setting.interaction

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.hmiyado.sampo.domain.setting.SettingMenu
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.result.interaction.SelectResultMenuItem

/**
 * Created by hmiyado on 2017/04/09.
 */
val settingMenuUseCaseModule = Kodein.Module {
    //    bind<CalculateTotalDistance>() with singleton { CalculateTotalDistance(instance(), instance(), instance()) }
    bind<SelectResultMenuItem<*>>() with singleton { SelectResultMenuItem<SettingMenu>(instance(), instance()) }
    bind<List<Interaction<*>>>() with singleton { listOf(instance<SelectResultMenuItem<*>>()) }
}
