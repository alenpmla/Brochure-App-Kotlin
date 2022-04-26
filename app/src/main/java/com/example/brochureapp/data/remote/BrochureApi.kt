package com.example.brochureapp.data.remote

import com.example.brochureapp.data.remote.dto.BrochureDataDto
import retrofit2.http.GET

interface BrochureApi {

    @GET("/stories-test/shelf.json")
    suspend fun getBrochureData(
    ): BrochureDataDto
}