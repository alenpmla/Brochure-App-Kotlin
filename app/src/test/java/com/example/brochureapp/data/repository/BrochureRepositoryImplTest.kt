package com.example.brochureapp.data.repository

import com.example.brochureapp.common.Resource
import com.example.brochureapp.data.remote.BrochureApi
import com.example.brochureapp.data.remote.dto.*
import com.example.brochureapp.domain.entities.ContentTypes
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import java.io.IOException

class BrochureRepositoryImplTest {
    private lateinit var mockBrochureApi: BrochureApi
    private lateinit var repository: BrochureRepositoryImpl

    @Before
    fun setUp() {
        mockBrochureApi = mock()
        repository = BrochureRepositoryImpl(mockBrochureApi)
    }

    @Test
    fun `is loading is true on getBrochureData`() = runBlocking {
        Mockito.`when`(mockBrochureApi.getBrochureData()).thenReturn(null)
        val firstItem = repository
            .getAllBrochures().first()
        assertThat((firstItem as Resource.Loading).isLoading).isEqualTo(true)
    }

    @Test
    fun `is loading is false after getBrochureData`() = runBlocking {
        Mockito.`when`(mockBrochureApi.getBrochureData()).thenReturn(null)
        val secondItem = repository
            .getAllBrochures().drop(1).first()
        assertThat((secondItem as Resource.Loading).isLoading).isEqualTo(false)
    }

    @Test
    fun `Resource state should be error when there is an exception `() = runBlocking {
        Mockito.`when`(mockBrochureApi.getBrochureData())
            .doAnswer { throw IOException() }
        val secondItem = repository
            .getAllBrochures().drop(1).first()
        assertThat((secondItem as Resource.Error).message).isEqualTo("Couldn't load data")
    }

    @Test
    fun `After an error, Loading should be false`() = runBlocking {
        Mockito.`when`(mockBrochureApi.getBrochureData())
            .doAnswer { throw IOException() }
        val thirdItem = repository
            .getAllBrochures().drop(2).first()
        assertThat((thirdItem as Resource.Loading).isLoading).isFalse()
    }

    @Test
    fun `Return valid content item, if data is available`() = runBlocking {
        Mockito.`when`(mockBrochureApi.getBrochureData()).thenReturn(successResponse)
        val secondItem = repository
            .getAllBrochures().drop(1).first()
        assertThat((secondItem as Resource.Success).data?.size).isEqualTo(contentsList.size)
    }

    @Test
    fun `loading state should set as failed after the success response`() = runBlocking {
        Mockito.`when`(mockBrochureApi.getBrochureData()).thenReturn(successResponse)
        val thirdItem = repository
            .getAllBrochures().drop(2).first()
        assertThat((thirdItem as Resource.Loading).isLoading).isFalse()
    }

    /*
    expected object of success
     */
    private var singleContent = ContentDto(
        adFormat = "Any",
        content = mapOf(
            "brochureImage" to "brochureImage",
            "distance" to 4,
            "id" to 1623228491,
            "title" to "Item Title",
            "retailer" to mapOf(
                "id" to 23,
                "name" to "Alen",
            ),
        ),
        contentFormatSource = "Any",
        contentType = ContentTypes.BrochurePremium.type,
        externalTracking = ExternalTrackingDto(
            click = emptyList(),
            impression = emptyList()
        ),
        placement = "Any"

    )

    private var contentsList = listOf(
        singleContent,
        singleContent,
        singleContent,
        singleContent,
        singleContent
    )
    private var successResponse: BrochureDataDto = BrochureDataDto(
        _embedded = EmbeddedDto(
            contents = contentsList,
        ),
        _links = LinksDto(self = SelfDto(href = "any")),
        page = PageDto(
            number = 2,
            size = 234,
            totalElements = 34,
            totalPages = 34
        )
    )
}