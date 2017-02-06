package com.example.hmiyado.sampo.view.result

import android.os.Bundle
import com.example.hmiyado.sampo.view.result.ui.ResultActivityUi
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import org.jetbrains.anko.setContentView

class ResultActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = ResultActivityUi()
        ui.setContentView(this)

        if (fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(ResultActivityUi.ROOT_VIEW_ID, ResultFragment())
                    .commit()
        }
    }
}
