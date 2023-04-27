package kz.kmf.mca.ui.custom_views.stories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dnapayments.domain.presentation.Story
import com.dnapayments.presentation.stories.StoryDisplayFragment

class StoryPagerAdapter(
    activity: FragmentActivity,
    private val storyList: ArrayList<Story>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun createFragment(position: Int): Fragment =
        StoryDisplayFragment.newInstance(position, storyList[position])

}
