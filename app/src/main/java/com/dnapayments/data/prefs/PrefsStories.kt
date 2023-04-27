package com.dnapayments.data.prefs

import android.content.Context
import com.dnapayments.utils.base.BasePrefs

/*  Storage for Stories shown to user  */
class PrefsStories(context: Context) : BasePrefs(context, PREFS_STORIES) {

    companion object {
        private const val PREFS_STORIES = "PrefsStories"
    }

    fun isStoriesShownToUser(idStory: String): Boolean = prefs?.getBoolean(idStory, false) ?: false
    fun saveStoriesShownById(idStory: String) = prefs?.edit()?.putBoolean(idStory, true)?.apply()
}