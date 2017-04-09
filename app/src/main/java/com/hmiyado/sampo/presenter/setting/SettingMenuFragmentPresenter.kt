package com.hmiyado.sampo.presenter.result

import com.hmiyado.sampo.domain.setting.SettingMenu
import com.hmiyado.sampo.usecase.result.UseMenuRequester
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hmiyado on 2017/02/06.
 */
class SettingMenuFragmentPresenter(
) : UseMenuRequester<SettingMenu> {
    private val fragmentRequester = PublishSubject.create<SettingMenu>()
    val fragmentRequest: Observable<SettingMenu> = fragmentRequester.hide().share()

    override fun request(menuItem: SettingMenu) {
        fragmentRequester.onNext(menuItem)
    }

}