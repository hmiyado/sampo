package com.example.hmiyado.sampo.service

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.hmiyado.sampo.activity.LocationAndroidServicePlayActivity
import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationService
import com.example.hmiyado.sampo.usecase.interaction.store.LocationServiceToStoreInteraction
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.factory
import com.github.salomonbrys.kodein.instance
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

/**
 * Created by hmiyado on 2017/01/30.
 */
class LocationAndroidService : IntentService("LocationAndroidService"), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val notificationManagerFactory: (Context) -> NotificationManager by injector.factory()
    private val locationService: LocationService by injector.instance()
    private val store = Store
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        inject(appKodein())
        subscriptions += LocationServiceToStoreInteraction(locationService, store).subscriptions

        val notifyIntent = Intent(baseContext, LocationAndroidServicePlayActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(baseContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = Notification.Builder(baseContext)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .addAction(Notification.Action.Builder(android.R.drawable.ic_media_play, "play", pendingIntent).build())
                .setStyle(
                        Notification.MediaStyle()
                                .setShowActionsInCompactView(0)
                )
                .setContentTitle("content title")
                .setContentText("content text")
                .build()
        notificationManagerFactory(baseContext).notify(notification.hashCode(), notification)
        startForeground(notification.hashCode(), notification)
    }

    override fun onHandleIntent(p0: Intent?) {
        p0 ?: return

        Timber.d("onHandleIntent: %s", p0.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        subscriptions.unsubscribe()
    }
}