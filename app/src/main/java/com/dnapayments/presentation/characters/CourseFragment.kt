package com.dnapayments.presentation.characters

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentCourseBinding
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CourseFragment :
    BaseBindingFragment<FragmentCourseBinding, CourseViewModel>(R.layout.fragment_course) {
    private var characterAdapter: CourseMainAdapter? = null
    override val vm: CourseViewModel by sharedViewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            characterAdapter = CourseMainAdapter {
                findNavController().navigate(R.id.action_course_to_lesson, Bundle().apply {
                    putInt(Constants.COURSE_ID, it.id)
                    putString(Constants.COURSE_TITLE, it.title)
                })
            }
            recyclerView.adapter = characterAdapter
            vm.coursesList.observe(viewLifecycleOwner, {
                characterAdapter?.setData(it)
            })
            vm.isRefreshing.observe(viewLifecycleOwner, {
                refresh.isRefreshing = it
            })
            refresh.setOnRefreshListener {
                vm.fetchCourses()
            }
        }
    }
}