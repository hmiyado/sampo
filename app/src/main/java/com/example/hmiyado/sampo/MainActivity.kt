package com.example.hmiyado.sampo

import android.os.Bundle
import com.example.hmiyado.sampo.view.MapFragment
import com.example.hmiyado.sampo.view.ui.MainActivityUi
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import org.jetbrains.anko.setContentView

class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = MainActivityUi()
        ui.setContentView(this)

        if (fragmentManager.findFragmentById(MainActivityUi.ROOT_VIEW_ID) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(MainActivityUi.ROOT_VIEW_ID, MapFragment())
                    .commit()
        }
    }
}
