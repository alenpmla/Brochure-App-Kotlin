package com.example.brochureapp.data.mappers

import com.example.brochureapp.domain.entities.Content
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MappersKtTest {

    private lateinit var sampleMap: Map<*, *>

    @Before
    fun setUp() {
        sampleMap = mapOf(
            "brochureImage" to "brochureImage",
            "distance" to 3.34,
            "id" to 44.4,
            "title" to "Item Title",
            "retailer" to mapOf(
                "id" to 34.34,
                "name" to "Alen",
            ),
        )
    }

    @Test
    fun `is mapToContent returns an instance of Content`() = runBlocking {
        assertThat(
            sampleMap.mapToContent(contentType = "brochure")
        ).isInstanceOf(Content::class.java)
    }

    @Test
    fun `is mapToContent returns Content with valid data`() = runBlocking {

        val content:Content = sampleMap.mapToContent(contentType = "brochure");
        assertThat(
            content.brochureImage
        ).isEqualTo(sampleMap["brochureImage"])
    }
}