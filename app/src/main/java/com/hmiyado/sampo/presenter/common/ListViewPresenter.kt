package com.hmiyado.sampo.presenter.common

import android.widget.ListView
import com.hmiyado.sampo.presenter.ViewPresenter
import com.hmiyado.sampo.usecase.common.UseListView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hmiyado on 2017/02/08.
 */
class ListViewPresenter<T>(view: ListView) : ViewPresenter<ListView>(view), UseListView.Source<T> {
    private val onItemSelectedObservable: PublishSubject<T> = PublishSubject.create<T>()

    @Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
    override fun getSelectedItem(): Observable<T> {
        view.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.adapter.getItem(i) as T
            onItemSelectedObservable.onNext(item)
        }
        return onItemSelectedObservable.hide().share()
    }
}