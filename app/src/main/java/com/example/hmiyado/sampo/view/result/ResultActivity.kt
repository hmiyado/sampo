package com.example.hmiyado.sampo.view.result

import android.app.Fragment
import android.os.Bundle
import com.example.hmiyado.sampo.view.common.FragmentRequester
import com.example.hmiyado.sampo.view.result.ui.ResultActivityUi
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import org.jetbrains.anko.setContentView

class ResultActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = ResultActivityUi()
        ui.setContentView(this)

        if (fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID) == null) {
            commitFragment(ResultMenuFragment())
        } else {
            subscribeFragmentRequester(fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID))
        }
    }

    private fun commitFragment(fragment: Fragment) {
        fragmentManager
                .beginTransaction()
                .replace(ResultActivityUi.ROOT_VIEW_ID, fragment, fragment.tag)
                .commit()

        subscribeFragmentRequester(fragment)
    }

    private fun subscribeFragmentRequester(fragment: Fragment) {
        if (fragment is FragmentRequester<*>) {
            fragment.getFragmentRequest()
                    .filter { it is ResultFragmentType }
                    .cast(ResultFragmentType::class.java)
                    .bindToLifecycle(this)
                    .subscribe { resultFragmentType ->
                        when (resultFragmentType) {
                            ResultFragmentType.Menu  -> commitFragment(ResultMenuFragment())
                            ResultFragmentType.Realm -> commitFragment(ResultRealmFragment())
                            else                     -> {
                            }
                        }
                    }
        }
    }
}
