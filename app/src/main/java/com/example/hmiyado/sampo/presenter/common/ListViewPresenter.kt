package com.example.hmiyado.sampo.presenter.common

import android.widget.ListView
import com.example.hmiyado.sampo.presenter.ViewPresenter
import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.Subject

/**
 * Created by hmiyado on 2017/02/08.
 */
class ListViewPresenter<T>(view: ListView) : ViewPresenter<ListView>(view) {
    val onItemSelectedObservable: Subject<T, T> = PublishSubject.create<T>()

    @Suppress("UNCHECKED_CAST")
    fun getItemSelectedObservable(): Observable<T> {
        view.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.adapter.getItem(i) as T
            onItemSelectedObservable.onNext(item)
        }
        return onItemSelectedObservable.asObservable().share()
    }

    fun setItems(list: List<T>) {
    }
}