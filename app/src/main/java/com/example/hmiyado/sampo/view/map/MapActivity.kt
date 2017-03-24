package com.example.hmiyado.sampo.view.map

import android.os.Bundle
import com.example.hmiyado.sampo.view.map.ui.MapActivityUi
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.setContentView

class MapActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = MapActivityUi()
        ui.setContentView(this)

        if (fragmentManager.findFragmentById(MapActivityUi.ROOT_VIEW_ID) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(MapActivityUi.ROOT_VIEW_ID, MapFragment())
                    .commit()
        }
    }
}
