package com.example.brochureapp.data.repository

import com.example.brochureapp.common.Resource
import com.example.brochureapp.data.mappers.mapToContent
import com.example.brochureapp.data.remote.BrochureApi
import com.example.brochureapp.domain.entities.ContentTypes
import com.example.brochureapp.domain.entities.Content
import com.example.brochureapp.domain.repository.BrochureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrochureRepositoryImpl @Inject constructor(
    private val api: BrochureApi,

    ) : BrochureRepository {


    override suspend fun getAllBrochures(): Flow<Resource<List<Content>>> {
        return flow {
            emit(value = Resource.Loading(true))

            val remoteListings = try {
                val response = api.getBrochureData()
                response
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                emit(Resource.Success(
                    data = listings
                        ._embedded.contents.filter {
                            it.contentType == ContentTypes.BrochurePremium.type || it.contentType == ContentTypes.Brochure.type
                        }.map {
                            val map = it.content as Map<*, *>
                            map.mapToContent(it.contentType)
                        }.filter {
                            it.distance <= 5
                        }
                ))
                emit(
                    Resource.Loading(
                        isLoading = false
                    )
                )
                return@flow
            }

            emit(
                Resource.Loading(
                    isLoading = false
                )
            )
        }
    }

}