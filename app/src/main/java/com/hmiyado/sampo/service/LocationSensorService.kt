package com.hmiyado.sampo.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.IBinder
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import com.hmiyado.sampo.R
import com.hmiyado.sampo.libs.rx.RxService
import com.hmiyado.sampo.libs.rx.ServiceEvent
import com.hmiyado.sampo.repository.location.LocationSensor
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.interaction.locationSensorUseCaseModule
import com.hmiyado.sampo.view.map.MapActivity
import timber.log.Timber

/**
 * Created by hmiyado on 2017/01/30.
 *
 * バックグラウンドで位置情報を取得，更新してくれるサービス．
 * 通知欄から操作できる．
 */
class LocationSensorService : RxService(), LazyKodeinAware {
    enum class IntentType {
        START,
        STOP;

        fun toIntent(context: Context): Intent {
            return Intent(context, LocationSensorService::class.java)
                    .putExtra(IntentType::class.simpleName, this)
        }
    }

    override val kodein: LazyKodein = LazyKodein {
        Kodein {
            extend(appKodein())
            import(locationSensorUseCaseModule)
        }
    }

    private val notificationManagerFactory: (Context) -> NotificationManager by kodein.factory()
    private val locationSensor: LocationSensor by kodein.instance()
    private val interactions: List<Interaction<*>> by kodein.instance()


    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")

        Interaction.Builder(this, ServiceEvent.DESTROY).buildAll(interactions)
    }

    private fun createCloseAction(): Notification.Action {
        val notifyIntent = IntentType.STOP.toIntent(this)
        val pendingIntent = PendingIntent.getService(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Notification.Action.Builder(
                    Icon.createWithResource(baseContext, android.R.drawable.ic_menu_close_clear_cancel),
                    IntentType.STOP.name.toLowerCase(),
                    pendingIntent
            )
        } else {
            @Suppress("DEPRECATION")
            Notification.Action.Builder(
                    R.drawable.ic_close_white_36dp,
                    IntentType.STOP.name.toLowerCase(),
                    pendingIntent
            )
        }

        return builder.build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: return super.onStartCommand(intent, flags, startId)

        Timber.d("onStartCommand: %s", intent.toString())
        val intentType = intent.getSerializableExtra(IntentType::class.simpleName) as IntentType
        Timber.d("Intent: %s", intentType)
        when (intentType) {
            LocationSensorService.IntentType.START -> {
                val notification = createNotification()
                notificationManagerFactory(baseContext).notify(notification.hashCode(), notification)
                startForeground(notification.hashCode(), notification)
                locationSensor.startLocationObserve()
                return START_STICKY
            }
            LocationSensorService.IntentType.STOP  -> {
                notificationManagerFactory(this).cancelAll()
                locationSensor.stopLocationObserve()
                stopSelf()
                return START_NOT_STICKY
            }
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(baseContext, MapActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
                baseContext,
                0,
                intent,
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)

        return Notification.Builder(applicationContext)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .addAction(createCloseAction())
                .setStyle(
                        Notification.MediaStyle()
                                .setShowActionsInCompactView(0)
                )
                .setContentTitle("さんぽ中")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_directions_walk_black_48dp)
                .build()
    }

    override fun onBind_(p0: Intent?): IBinder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}