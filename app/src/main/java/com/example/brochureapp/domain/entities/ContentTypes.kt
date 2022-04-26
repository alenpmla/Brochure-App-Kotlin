package com.example.brochureapp.domain.entities


sealed class ContentTypes(val type: String) {
    object BrochurePremium : ContentTypes(type = "brochurePremium")
    object Brochure : ContentTypes(type = "brochure")
}