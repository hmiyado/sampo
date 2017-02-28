package com.example.hmiyado.sampo.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.repository.location.LocationSensor
import com.example.hmiyado.sampo.usecase.map.interaction.SaveLocation
import com.example.hmiyado.sampo.usecase.map.interaction.UpdateLocation
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.ServiceInjector
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
class LocationService : Service(), ServiceInjector {
    enum class IntentType {
        START,
        STOP
    }

    override val injector: KodeinInjector = KodeinInjector()

    private val notificationManagerFactory: (Context) -> NotificationManager by injector.factory()
    private val locationSensor: LocationSensor by injector.instance()
    private val locationRepository: LocationRepository by injector.instance()
    private val store: MapStore by injector.instance()
    private val subscriptions: CompositeSubscription = CompositeSubscription()


    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        initializeInjector()
        subscriptions += UpdateLocation(locationSensor, store).subscriptions
        subscriptions += SaveLocation(store, locationRepository).subscriptions
    }

    private fun createCloseAction(): Notification.Action {
        val notifyIntent = Intent(this, LocationService::class.java)
                .putExtra(IntentType::class.simpleName, IntentType.STOP)
        val pendingIntent = PendingIntent.getService(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        return Notification.Action.Builder(
                android.R.drawable.ic_menu_close_clear_cancel,
                IntentType.STOP.name.toLowerCase(),
                pendingIntent
        ).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: return super.onStartCommand(intent, flags, startId)

        Timber.d("onStartCommand: %s", intent.toString())
        val intentType = intent.getSerializableExtra(IntentType::class.simpleName) as IntentType
        Timber.d("Intent: %s", intentType)
        when (intentType) {
            LocationService.IntentType.START -> {
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
                locationSensor.startLocationObserve()
                return START_STICKY
            }
            LocationService.IntentType.STOP  -> {
                notificationManagerFactory(this).cancelAll()
                locationSensor.stopLocationObserve()
                stopSelf()
                return START_NOT_STICKY
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        subscriptions.unsubscribe()
        destroyInjector()
    }

    override fun onBind(p0: Intent?): IBinder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}