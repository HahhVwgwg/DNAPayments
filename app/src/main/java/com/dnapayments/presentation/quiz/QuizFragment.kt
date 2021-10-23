package com.dnapayments.presentation.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.data.model.Result
import com.dnapayments.databinding.FragmentQuizBinding
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizFragment :
    BaseBindingFragment<FragmentQuizBinding, QuizViewModel>(R.layout.fragment_quiz) {
    override val vm: QuizViewModel by viewModel()
    private var optionAdapter: OptionAdapter? = null
    private var resultAdapter: ResultAdapter? = null
    override fun initViews(savedInstanceState: Bundle?) {
        val lessonId = arguments?.getInt(Constants.LESSON_ID) ?: -1
        val passed = arguments?.getBoolean(Constants.PASSED) ?: false
        binding?.apply {
            viewModel = vm
            end.setOnClickListener {
                onBackPressed()
            }
            println(passed)

            // get quizzes from server
            vm.fetchQuizzesById(lessonId)

            vm.passed = passed

            //init adapter
            resultAdapter = ResultAdapter()
            optionAdapter = OptionAdapter {
                Handler(Looper.getMainLooper()).postDelayed({
                    vm.showNextOption(it)
                    optionAdapter?.activateOptions()
                }, 500)
            }
            vm.isRefreshing.observe(viewLifecycleOwner, {
                optionAdapter?.deactivateOptions()
            })
            recyclerView.adapter = optionAdapter
            recyclerViewScore.adapter = resultAdapter

            //observers
            vm.options.observe(viewLifecycleOwner, {
                optionAdapter?.setData(it.shuffled())
            })
            vm.progress.observe(viewLifecycleOwner, {
                resultAdapter?.setData(it)
            })
            vm.endOfQuiz.observe(viewLifecycleOwner, {
                findNavController().navigate(R.id.action_quiz_to_result, Bundle().apply {
                    putParcelable(Constants.RESULT,
                        Result(total = vm.totalSize.get(), result = vm.totalAnsweredOptions))
                    putInt(Constants.LESSON_ID, lessonId)
                    putBoolean(Constants.PASSED, passed)
                })
            })

        }
    }
}