package com.example.brochureapp.data.remote.dto


data class ContentDto(
    val adFormat: Any,
    val content: Any,
    val contentFormatSource: String,
    val contentType: String,
    val externalTracking: ExternalTrackingDto,
    val placement: String
)
