package com.example.hmiyado.sampo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hmiyado.sampo.view.ui.MainActivityUi
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SampoModule.attachActivity(this)

        val ui = MainActivityUi()
        ui.setContentView(this)

        supportFragmentManager
                .beginTransaction()
                .add(MainActivityUi.ROOT_VIEW_ID, com.example.hmiyado.sampo.view.MapFragment())
                .commit()
    }
}
