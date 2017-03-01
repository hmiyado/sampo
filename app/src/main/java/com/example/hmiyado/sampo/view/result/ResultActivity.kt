package com.example.hmiyado.sampo.view.result

import android.app.Fragment
import android.os.Bundle
import com.example.hmiyado.sampo.view.common.FragmentRequester
import com.example.hmiyado.sampo.view.result.ui.ResultActivityUi
import com.trello.rxlifecycle.android.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindUntilEvent
import org.jetbrains.anko.setContentView
import timber.log.Timber

class ResultActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = ResultActivityUi()
        ui.setContentView(this)

        if (fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID) == null) {
            commitFragment(ResultMenuFragment())
        }
    }

    override fun onBackPressed() {
        if (fragmentManager.popBackStackImmediate()) {
            val fragment = fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID)
            if (fragment == null) {
                finish()
                return
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val fragment = fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID)
        if (fragment == null) {
            finish()
            return
        }
        subscribeFragmentRequester(fragment)
    }

    private fun commitFragment(fragment: Fragment) {
        fragmentManager
                .beginTransaction()
                .addToBackStack(fragment.tag)
                .replace(ResultActivityUi.ROOT_VIEW_ID, fragment, fragment.tag)
                .commit()
    }

    private fun subscribeFragmentRequester(fragment: Fragment) {
        if (fragment is FragmentRequester<*>) {
            fragment.getFragmentRequest()
                    .filter { it is ResultFragmentType }
                    .cast(ResultFragmentType::class.java)
                    .bindUntilEvent(this, ActivityEvent.STOP)
                    .subscribe({ resultFragmentType ->
                        Timber.d(resultFragmentType.toString())
                        when (resultFragmentType) {
                            ResultFragmentType.Menu    -> commitFragment(ResultMenuFragment())
                            ResultFragmentType.Realm   -> commitFragment(ResultRepositoryFragment())
                            ResultFragmentType.Summary -> commitFragment(ResultSummaryFragment())
                            else                       -> {
                            }
                        }
                    }, {}, {
                        Timber.d("onComplete")
                    })
        }
    }
}
