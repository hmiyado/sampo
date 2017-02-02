package com.example.hmiyado.sampo.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.repository.location.LocationService
import com.example.hmiyado.sampo.usecase.interaction.locationrepository.StoreToLocationRepositoryInteraction
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
 *
 * バックグラウンドで位置情報を取得，更新してくれるサービス．
 * 通知欄から操作できる．
 */
class LocationAndroidService : Service(), KodeinInjected {
    enum class IntentType {
        START,
        CLOSE
    }

    override val injector: KodeinInjector = KodeinInjector()

    private val notificationManagerFactory: (Context) -> NotificationManager by injector.factory()
    private val locationService: LocationService by injector.instance()
    private val locationRepository: LocationRepository by injector.instance()
    private val store = Store
    private val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        inject(appKodein())
        subscriptions += LocationServiceToStoreInteraction(locationService, store).subscriptions
        subscriptions += StoreToLocationRepositoryInteraction(store, locationRepository).subscriptions
    }

    private fun createCloseAction(): Notification.Action {
        val notifyIntent = Intent(this, LocationAndroidService::class.java)
                .putExtra(IntentType::class.simpleName, IntentType.CLOSE)
        val pendingIntent = PendingIntent.getService(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        return Notification.Action.Builder(
                android.R.drawable.ic_menu_close_clear_cancel,
                IntentType.CLOSE.name.toLowerCase(),
                pendingIntent
        ).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: return super.onStartCommand(intent, flags, startId)

        Timber.d("onStartCommand: %s", intent.toString())
        val intentType = intent.getSerializableExtra(IntentType::class.simpleName) as IntentType
        Timber.d("Intent: %s", intentType)
        when (intentType) {
            LocationAndroidService.IntentType.START -> {
                val notification = Notification.Builder(applicationContext)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .addAction(createCloseAction())
                        .setStyle(
                                Notification.MediaStyle()
                                        .setShowActionsInCompactView(0)
                        )
                        .setContentTitle("content title")
                        .setContentText("content text")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .build()
                Timber.d(notification.toString())
                notificationManagerFactory(baseContext).notify(notification.hashCode(), notification)
                startForeground(notification.hashCode(), notification)
                locationService.startLocationObserve()
                return START_STICKY
            }
            LocationAndroidService.IntentType.CLOSE -> {
                notificationManagerFactory(this).cancelAll()
                locationService.stopLocationObserve()
                stopSelf()
                return START_NOT_STICKY
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        subscriptions.unsubscribe()
    }

    override fun onBind(p0: Intent?): IBinder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}