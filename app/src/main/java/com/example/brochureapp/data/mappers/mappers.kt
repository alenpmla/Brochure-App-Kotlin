package com.example.brochureapp.data.mappers

import com.example.brochureapp.domain.entities.Content
import com.example.brochureapp.domain.entities.Retailer

fun Map<*, *>.mapToContent(contentType: String): Content {
    return Content(
        brochureImage = this["brochureImage"] as String,
        distance = this["distance"].toString().toDouble(),
        id = this["id"].toString(),
        contentType = contentType,
        title = this["title"] as String,
        retailer = if (this["retailer"] != null) (this["retailer"] as Map<*, *>).mapToRetailer() else null,
    )
}

fun Map<*, *>.mapToRetailer(): Retailer {
    return Retailer(
        id = this["id"] as Any,
        name = this["name"] as String,
    )
}