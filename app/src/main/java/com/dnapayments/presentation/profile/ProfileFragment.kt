package com.dnapayments.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.databinding.FragmentProfileBinding
import com.dnapayments.presentation.activity.RegistrationActivity
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.presentation.pin_code.PinCodeFragment
import com.dnapayments.utils.Constants.NEWS_LIST
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class ProfileFragment :
    BaseBindingFragment<FragmentProfileBinding, MainViewModel>(R.layout.fragment_profile) {
    private val prefsAuth: PrefsAuth by inject()
    override val vm: MainViewModel by sharedViewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            logOutBtn.setOnClickListener {
                prefsAuth.logout()
                startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
                requireActivity().finishAffinity()
            }
            changePinCode.setOnClickListener {
                PinCodeFragment.newInstance(if (prefsAuth.isPinCodeExist()) 11 else 10).show(
                    parentFragmentManager,
                    null
                )
            }

            news.setOnClickListener {
                findNavController().navigate(R.id.action_profile_to_news, Bundle().apply {
                    putParcelableArrayList(NEWS_LIST, vm.news as ArrayList<NotificationElement>)
                })
            }
            knowledge.setOnClickListener {
                findNavController().navigate(R.id.action_profile_to_knowledge)
            }
        }
    }
}