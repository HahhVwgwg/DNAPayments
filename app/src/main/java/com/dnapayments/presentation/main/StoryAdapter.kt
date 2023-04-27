package com.dnapayments.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnapayments.R
import com.dnapayments.domain.presentation.Story

class StoryAdapter(
    var storyList: ArrayList<Story>,
    private var listener: (list: ArrayList<Story>, position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val isStoriesShownList = arrayListOf<Boolean>()
    private val skeletonList = arrayListOf(1, 2, 3)
    private var type: Int = -1

    init {
        loadSkeleton()
    }

    private fun loadSkeleton() = notifyDataSetChanged()

    override fun getItemCount(): Int =
        if (skeletonList.size > 0) skeletonList.size else storyList.size

    interface StoryListener {
        fun onStorySelected(storyList: ArrayList<Story>, position: Int)
    }

    override fun getItemViewType(position: Int): Int = type

    fun updateShownStoriesList(shownStoriesList: List<Boolean>) {
        if (isStoriesShownList.isNotEmpty()) isStoriesShownList.clear()
        isStoriesShownList.addAll(shownStoriesList)
        notifyDataSetChanged()
    }

    fun setStories(list: List<Story>, shownStoriesList: List<Boolean>) {
        if (isStoriesShownList.isNotEmpty()) isStoriesShownList.clear()
        if (storyList.isNotEmpty()) storyList.clear()
        if (skeletonList.isNotEmpty()) skeletonList.clear()
        isStoriesShownList.addAll(shownStoriesList)
        type = 1
        storyList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (getItemViewType(position) == 1) {
            if (holder is ViewHolder) {
                holder.view.setOnClickListener {
                    listener.invoke(storyList, position)
                }
                holder.bind(storyList[position], isStoriesShownList[position])
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        if (viewType == 1) {
            ViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_item_story, parent, false)
            )
        } else {
            SkeletonVH(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_item_story_skeleton, parent, false)
            )
        }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Story, isShown: Boolean) {
            with(view) {
                findViewById<TextView>(R.id.story_title).text = data.title
                view.findViewById<View>(R.id.border).background = ContextCompat.getDrawable(
                    context,
                    if (isShown) R.drawable.story_border_shown else R.drawable.story_border
                )
                Glide.with(context)
                    .load(data.image)
                    .centerCrop()
                    .into(findViewById(R.id.story_image))
            }
        }
    }


    inner class SkeletonVH(view: View) : RecyclerView.ViewHolder(view)

}
