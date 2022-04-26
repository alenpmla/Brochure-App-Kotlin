package com.example.brochureapp.presentation.viewmodel

import com.example.brochureapp.domain.entities.Content

data class BrochureListingState(
    val brochureContentList: List<Content> = emptyList(),
    val isLoading: Boolean = false,
)
