package com.example.brochureapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BrochureDataDto(
    @SerializedName("_embedded")
    val _embedded: EmbeddedDto,
    @SerializedName("_links")
    val _links: LinksDto,
    val page: PageDto
)