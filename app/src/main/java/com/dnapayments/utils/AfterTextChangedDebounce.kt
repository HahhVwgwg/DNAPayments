package com.dnapayments.utils

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.*

fun SearchView.afterTextChangedDebounce(input: (String) -> Unit) {
    val delayMillis = 500L
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            callSearch(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            callSearch(newText)
            return true
        }

        fun callSearch(query: String) {
            debounceJob?.cancel()
            if (lastInput != query) {
                lastInput = query
                debounceJob = uiScope.launch {
                    delay(delayMillis)
                    if (lastInput == query) {
                        input(query)
                    }
                }
            }
        }
    })
}