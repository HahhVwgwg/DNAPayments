package com.dnapayments.presentation.knowledge_details

import androidx.databinding.ObservableField
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.utils.base.BaseViewModel

class KnowledgeDetailsViewModel : BaseViewModel() {
    val knowledgeDetails = ObservableField<NotificationElement>()
}