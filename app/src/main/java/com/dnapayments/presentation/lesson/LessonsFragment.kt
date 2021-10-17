package com.dnapayments.presentation.lesson

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentLessonBinding
import com.dnapayments.presentation.activity.MainActivity
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonsFragment :
    BaseBindingFragment<FragmentLessonBinding, LessonViewModel>(R.layout.fragment_lesson) {
    override val vm: LessonViewModel by viewModel()
    private var adapter: LessonAdapter? = null
    override fun initViews(savedInstanceState: Bundle?) {
        val courseId = arguments?.getInt(Constants.COURSE_ID) ?: -1
        val courseTitle = arguments?.getString(Constants.COURSE_TITLE) ?: ""
        binding?.apply {
            viewModel = vm
            adapter = LessonAdapter({
                if (activity is MainActivity) {
                    (activity as MainActivity).toggleVisibility(false)
                }
                findNavController().navigate(R.id.action_lesson_to_details, Bundle().apply {
                    putString(Constants.VIDEO_URL, it.videoUrl)
                    putString(Constants.COURSE_TITLE, it.title)
                    putInt(Constants.LESSON_ID, it.id)
                    putBoolean(Constants.PASSED, it.result?.passed == 1)
                })
            }, {
                vm.error.value = R.string.pass_previous_lesson
            })
            back.setOnClickListener {
                onBackPressed()
            }
            vm.title.set(courseTitle)
            recyclerView.adapter = adapter
            vm.getLessonByCourseId(courseId)
            vm.isRefreshing.observe(viewLifecycleOwner, {
                refresh.isRefreshing = it
            })
            vm.lessonList.observe(viewLifecycleOwner, {
                it.forEachIndexed { index, item ->
                    item.position = index
                }
                adapter?.setData(it)
            })
            refresh.setOnRefreshListener {
                vm.getLessonByCourseId(courseId)
            }
        }
    }
}