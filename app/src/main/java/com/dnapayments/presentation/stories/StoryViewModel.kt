package com.dnapayments.presentation.stories

import com.dnapayments.data.repository.StoriesRepository
import com.dnapayments.utils.base.BaseViewModel

class StoryViewModel(val repository: StoriesRepository) : BaseViewModel() {
    fun isStoriesShownToUser(idStory: String): Boolean = repository.isStoriesShownToUser(idStory)
    fun saveStoriesShownById(idStory: String) = repository.saveStoriesShownById(idStory)
}