package com.hmiyado.sampo.view

import com.hmiyado.sampo.presenter.FragmentPresenter

/**
 * Created by hmiyado on 2016/07/27.
 */
interface FragmentWithPresenter {
    fun getPresenter(): FragmentPresenter
}