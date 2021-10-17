package com.dnapayments.data.model

data class Progress(
    var current: Boolean = false,
    var notAnswered: Boolean = true,
    var position: Int = 0,
    var answerWasTrue: Boolean = false,
)