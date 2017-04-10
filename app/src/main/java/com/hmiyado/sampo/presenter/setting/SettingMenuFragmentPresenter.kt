package com.hmiyado.sampo.presenter.result

import com.hmiyado.sampo.domain.setting.SettingMenu
import com.hmiyado.sampo.usecase.result.UseMenuRequester
import com.hmiyado.sampo.view.setting.SettingActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hmiyado on 2017/02/06.
 */
class SettingMenuFragmentPresenter(
) : UseMenuRequester<SettingMenu>, SettingActivity.SettingMenuRequester {
    private val fragmentRequester = PublishSubject.create<SettingMenu>()
    override val fragmentRequest: Observable<SettingMenu>
        get() = fragmentRequester.hide().share()

    override fun request(menuItem: SettingMenu) {
        fragmentRequester.onNext(menuItem)
    }

}