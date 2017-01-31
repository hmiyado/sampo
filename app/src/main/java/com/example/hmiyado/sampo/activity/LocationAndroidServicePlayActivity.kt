package com.example.hmiyado.sampo.activity

import android.content.Intent
import android.os.Bundle
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import timber.log.Timber

/**
 * Created by hmiyado on 2017/01/31.
 */
class LocationAndroidServicePlayActivity : RxAppCompatActivity() {
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) {
            return
        }
        Timber.d("onNewIntent: %s", intent.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}