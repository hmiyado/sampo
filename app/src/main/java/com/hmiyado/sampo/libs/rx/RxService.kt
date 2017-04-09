package com.hmiyado.sampo.libs.rx

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by hmiyado on 2017/03/23.
 */
abstract class RxService : Service(), LifecycleProvider<ServiceEvent> {
    private val lifecycleSubject = BehaviorSubject.create<ServiceEvent>()

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycle.bind(lifecycleSubject)
    }

    override fun lifecycle(): Observable<ServiceEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ServiceEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun onCreate() {
        super.onCreate()
        lifecycleSubject.onNext(ServiceEvent.CREATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleSubject.onNext(ServiceEvent.START_COMMAND)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ServiceEvent.DESTROY)
        super.onDestroy()
    }

    final override fun onBind(p0: Intent?): IBinder {
        lifecycleSubject.onNext(ServiceEvent.BIND)
        return onBind_(p0)
    }

    abstract fun onBind_(p0: Intent?): IBinder
}