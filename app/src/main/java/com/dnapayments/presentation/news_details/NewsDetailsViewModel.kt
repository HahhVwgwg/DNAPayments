package com.dnapayments.presentation.news_details

import androidx.databinding.ObservableField
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.utils.base.BaseViewModel

class NewsDetailsViewModel : BaseViewModel() {
    val newsDetails = ObservableField<NotificationElement>()

}