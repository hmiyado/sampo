package com.hmiyado.sampo.view.setting

import android.app.Fragment
import android.os.Bundle
import com.hmiyado.sampo.domain.setting.SettingMenu
import com.hmiyado.sampo.view.common.FragmentRequester
import com.hmiyado.sampo.view.common.ui.RxActivityUi
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.setContentView
import timber.log.Timber

/**
 * Created by hmiyado on 2017/04/09.
 */
class SettingActivity : RxAppCompatActivity() {
    interface SettingMenuRequester : FragmentRequester<SettingMenu>

    val ui = RxActivityUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        ui.setContentView(this)

        if (fragmentManager.findFragmentById(ui.ROOT_VIEW_ID) == null) {
            commitFragment(SettingMenuFragment.newInstance())
        }
    }

    //    override fun onBackPressed() {
    //        if (fragmentManager.popBackStackImmediate()) {
    //            val fragment = fragmentManager.findFragmentById(ui.ROOT_VIEW_ID)
    //            if (fragment == null) {
    //                finish()
    //                return
    //            }
    //        }
    //    }
    //
    //    override fun onStart() {
    //        super.onStart()
    //        val fragment = fragmentManager.findFragmentById(ui.ROOT_VIEW_ID)
    //        if (fragment == null) {
    //            finish()
    //            return
    //        }
    //        subscribeFragmentRequester(fragment)
    //    }
    //
    private fun commitFragment(fragment: Fragment) {
        fragmentManager
                .beginTransaction()
                .addToBackStack(fragment.tag)
                .replace(ui.ROOT_VIEW_ID, fragment, fragment.tag)
                .commit()
    }
    //
    //    private fun subscribeFragmentRequester(fragment: Fragment) {
    //        if (fragment is SettingMenuRequester) {
    //            fragment.fragmentRequest
    //                    .subscribeOn(Schedulers.newThread())
    //                    .bindUntilEvent(this, ActivityEvent.STOP)
    //                    .observeOn(AndroidSchedulers.mainThread())
    //                    .subscribe({ settingMenu ->
    //                        Timber.d(settingMenu.toString())
    //                        when (settingMenu) {
    //                            SettingMenu.ABOUT_APP -> {
    //                            }
    //                            SettingMenu.LICENCE   -> {
    //                            }
    //                            else                  -> {
    //                            }
    //                        }
    //                    }, {}, {
    //                        Timber.d("onComplete")
    //                    })
    //        }
    //    }
}