package com.example.brochureapp.domain.entities

data class Content(
    val brochureImage: String,
    val distance: Double,
    val contentType: String,
    val id: Any,
    val retailer: Retailer?,
    val title: String,
)



