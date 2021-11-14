package com.dnapayments.presentation.activity

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dnapayments.R
import com.dnapayments.databinding.ActivityMainBinding
import com.dnapayments.presentation.call.CallBottomFragment
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.presentation.pin_code.PinCodeFragment
import com.dnapayments.presentation.update.UpdateBottomFragment
import com.dnapayments.utils.OnItemSelectedListener
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingActivity
import com.dnapayments.utils.base.BaseBindingFragment
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main),
    OnItemSelectedListener {
    private val vm: MainViewModel by viewModel()
    private var mAppUpdateManager: AppUpdateManager? = null
    private var installStateUpdatedListener: InstallStateUpdatedListener? = null
    private val RC_APP_UPDATE = 11
    private val prefsAuth: PrefsAuth by inject()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            navView.setupWithNavController(findNavController(R.id.nav_host_fragment_main))
            navView.setOnItemSelectedListener { navigation ->
                if (NavigationUI.onNavDestinationSelected(
                        navigation,
                        findNavController(R.id.nav_host_fragment_main)
                    )
                ) {
                    true
                } else {
                    when (navigation.itemId) {
                        R.id.navigation_phone -> {
                            CallBottomFragment(
                                vm.profile.get()?.parkMobile,
                                vm.profile.get()?.supportMobile
                            ) {
                                if (it)
                                    startCall(vm.profile.get()?.parkMobile)
                                else
                                    startCall(vm.profile.get()?.supportMobile)
                            }.show(supportFragmentManager, null)
                            false
                        }
                        else -> false
                    }
                }
            }
            vm.mainBottomSheetSelectedItm.observe(this@MainActivity, {
                when (it) {
                    0 -> {
                        navView.selectedItemId = R.id.navigation_main
                    }
                    1 -> {
                        navView.selectedItemId = R.id.navigation_withdraw
                    }
                    3 -> {
                        navView.selectedItemId = R.id.navigation_phone
                    }
                    4 -> {
                        navView.selectedItemId = R.id.navigation_profile
                    }
                }
            })

        }
        PinCodeFragment.newInstance(if (prefsAuth.isPinCodeExist()) 1 else 10).show(
            supportFragmentManager,
            null
        )



        mAppUpdateManager = AppUpdateManagerFactory.create(this)

        mAppUpdateManager?.registerListener(installStateUpdatedListener)

        mAppUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() === UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)
            ) {
                try {
                    mAppUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/,
                        this,
                        RC_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            } else if (appUpdateInfo.installStatus() === InstallStatus.DOWNLOADED) {
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate()
            } else {
                Log.e(BaseBindingFragment.TAG, "checkForAppUpdateAvailability: something else")
            }
        }

        installStateUpdatedListener =
            object : InstallStateUpdatedListener {
                override fun onStateUpdate(state: InstallState) {
                    when {
                        state.installStatus() == InstallStatus.DOWNLOADED -> {
                            popupSnackbarForCompleteUpdate()
                        }
                        state.installStatus() == InstallStatus.INSTALLED -> {
                            mAppUpdateManager?.unregisterListener(this)
                        }
                        else -> {
                            Log.i(
                                BaseBindingFragment.TAG,
                                "InstallStateUpdatedListener: state: " + state.installStatus()
                            )
                        }
                    }
                }
            }
    }

    override fun onBackPressed() {
        binding?.navView?.let {
            when (NavHostFragment.findNavController(supportFragmentManager.fragments[0]).currentDestination?.id) {
                R.id.navigation_add_card,
                R.id.navigation_history,
                -> {
                    vm.viewState.set(true)
                }
                else -> {

                }
            }
            super.onBackPressed()
        }
    }

    private fun startCall(phoneNumber: String?) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e(BaseBindingFragment.TAG, "onActivityResult: app download failed")
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        UpdateBottomFragment("") {
            if (mAppUpdateManager != null) {
                mAppUpdateManager!!.completeUpdate()
            }
        }.show(supportFragmentManager, null)
    }


    override fun onStop() {
        super.onStop()
        if (mAppUpdateManager != null) {
            mAppUpdateManager?.unregisterListener(installStateUpdatedListener)
        }
    }

    override fun onBottomSheetCallback(value: Boolean) {
        vm.onPinCallback.value = value
    }
}