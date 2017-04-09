package com.hmiyado.sampo.view.result

import android.app.Fragment
import android.os.Bundle
import com.hmiyado.sampo.view.common.FragmentRequester
import com.hmiyado.sampo.view.common.ui.RxActivityUi
import com.hmiyado.sampo.view.result.repository.location.ResultLocationRepositoryFragment
import com.hmiyado.sampo.view.result.repository.marker.ResultMarkerRepositoryFragment
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.BuildConfig
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.setContentView
import timber.log.Timber

class ResultActivity : RxAppCompatActivity() {

    val ui = RxActivityUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        ui.setContentView(this)

        if (fragmentManager.findFragmentById(ui.ROOT_VIEW_ID) == null) {
            commitFragment(
                    if (BuildConfig.DEBUG) {
                        ResultMenuFragment.newInstance()
                    } else {
                        ResultSummaryFragment.newInstance()
                    }
            )
        }
    }

    override fun onBackPressed() {
        if (fragmentManager.popBackStackImmediate()) {
            val fragment = fragmentManager.findFragmentById(ui.ROOT_VIEW_ID)
            if (fragment == null) {
                finish()
                return
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val fragment = fragmentManager.findFragmentById(ui.ROOT_VIEW_ID)
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
                .replace(ui.ROOT_VIEW_ID, fragment, fragment.tag)
                .commit()
    }

    private fun subscribeFragmentRequester(fragment: Fragment) {
        if (fragment is FragmentRequester<*>) {
            fragment.fragmentRequest
                    .subscribeOn(Schedulers.newThread())
                    .filter { it is ResultFragmentType }
                    .cast(ResultFragmentType::class.java)
                    .bindUntilEvent(this, ActivityEvent.STOP)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ resultFragmentType ->
                        Timber.d(resultFragmentType.toString())
                        when (resultFragmentType) {
                            ResultFragmentType.Menu                -> commitFragment(ResultMenuFragment())
                            ResultFragmentType.LOCATION_REPOSITORY -> commitFragment(ResultLocationRepositoryFragment.newInstance())
                            ResultFragmentType.MARKER_REPOSITORY   -> commitFragment(ResultMarkerRepositoryFragment.newInstance())
                            ResultFragmentType.Summary             -> commitFragment(ResultSummaryFragment.newInstance())
                            else                                   -> {
                            }
                        }
                    }, {}, {
                        Timber.d("onComplete")
                    })
        }
    }
}
