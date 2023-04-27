package com.dnapayments.domain.presentation

data class SearchResult(
    val header: String,
    val subHeader: String
) {
    override fun toString(): String {
        return "SearchResult(header='$header', subHeader='$subHeader')"
    }
}
