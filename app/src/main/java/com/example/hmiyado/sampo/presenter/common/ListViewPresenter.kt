package com.example.hmiyado.sampo.presenter.common

import android.widget.ListView
import com.example.hmiyado.sampo.domain.result.ResultMenuItem
import com.example.hmiyado.sampo.presenter.ViewPresenter
import rx.Observable
import rx.subjects.PublishSubject

/**
 * Created by hmiyado on 2017/02/08.
 */
class ListViewPresenter(view: ListView) : ViewPresenter<ListView>(view) {
    fun getItemSelectedObservable(): Observable<ResultMenuItem> {
        val subject = PublishSubject.create<ResultMenuItem>()

        view.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.adapter.getItem(i) as ResultMenuItem
            subject.onNext(item)
        }
        return subject.asObservable().share()
    }
}