package com.dnapayments.presentation.result

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.data.model.Result
import com.dnapayments.databinding.FragmentResultBinding
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment :
    BaseBindingFragment<FragmentResultBinding, MainViewModel>(R.layout.fragment_result) {
    override val vm: MainViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        val lessonId = arguments?.getInt(Constants.LESSON_ID) ?: -1
        val result = arguments?.getParcelable(Constants.RESULT) ?: Result(0, 0)
        val wasExcellent: Boolean = (result.result / result.total) > 0.8
        binding?.apply {
            viewModel = vm
            vm.result.set(result.result)
            vm.total.set(result.total)
            vm.status.set(if (wasExcellent) getStr(R.string.excellent) else getStr(R.string.good))
            vm.motivational.set(if (wasExcellent) getStr(R.string.excellent_result) else getStr(R.string.good_result))
            repeatQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_result_to_quiz, Bundle().apply {
                    putInt(Constants.LESSON_ID, lessonId)
                })
            }
            goToMainPage.setOnClickListener {
                onBackPressed()
            }
        }
    }
}