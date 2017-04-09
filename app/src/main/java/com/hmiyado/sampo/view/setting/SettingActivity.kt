package com.hmiyado.sampo.view.setting

import android.os.Bundle
import android.os.PersistableBundle
import com.hmiyado.sampo.view.map.ui.RxActivityUi
import com.hmiyado.sampo.view.result.ui.ResultActivityUi
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.setContentView

/**
 * Created by hmiyado on 2017/04/09.
 */
class SettingActivity : RxAppCompatActivity() {
    val ui = RxActivityUi()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        ui.setContentView(this)

        if (fragmentManager.findFragmentById(ui.ROOT_VIEW_ID) == null) {

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
        //        val fragment = fragmentManager.findFragmentById(ResultActivityUi.ROOT_VIEW_ID)
        //        if (fragment == null) {
        //            finish()
        //            return
        //        }
        //        subscribeFragmentRequester(fragment)
    }

    //    private fun commitFragment(fragment: Fragment) {
    //        fragmentManager
    //                .beginTransaction()
    //                .addToBackStack(fragment.tag)
    //                .replace(ui.ROOT_VIEW_ID, fragment, fragment.tag)
    //                .commit()
    //    }
    //
    //    private fun subscribeFragmentRequester(fragment: Fragment) {
    //        if (fragment is FragmentRequester<*>) {
    //            fragment.getFragmentRequest()
    //                    .subscribeOn(Schedulers.newThread())
    //                    .filter { it is ResultFragmentType }
    //                    .cast(ResultFragmentType::class.java)
    //                    .bindUntilEvent(this, ActivityEvent.STOP)
    //                    .observeOn(AndroidSchedulers.mainThread())
    //                    .subscribe({ resultFragmentType ->
    //                        Timber.d(resultFragmentType.toString())
    //                        when (resultFragmentType) {
    //                            ResultFragmentType.Menu                -> commitFragment(ResultMenuFragment())
    //                            ResultFragmentType.LOCATION_REPOSITORY -> commitFragment(ResultLocationRepositoryFragment.newInstance())
    //                            ResultFragmentType.MARKER_REPOSITORY   -> commitFragment(ResultMarkerRepositoryFragment.newInstance())
    //                            ResultFragmentType.Summary             -> commitFragment(ResultSummaryFragment.newInstance())
    //                            else                                   -> {
    //                            }
    //                        }
    //                    }, {}, {
    //                        Timber.d("onComplete")
    //                    })
    //        }
    //    }
}