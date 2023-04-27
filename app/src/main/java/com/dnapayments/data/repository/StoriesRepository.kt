package com.dnapayments.data.repository

import com.dnapayments.data.prefs.PrefsStories

class StoriesRepository(private val prefs: PrefsStories) {
    fun isStoriesShownToUser(idStory: String): Boolean = prefs.isStoriesShownToUser(idStory)
    fun saveStoriesShownById(idStory: String) = prefs.saveStoriesShownById(idStory)
}