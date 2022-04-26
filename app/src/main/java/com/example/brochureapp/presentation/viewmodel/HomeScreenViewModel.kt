package com.example.brochureapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brochureapp.common.Resource
import com.example.brochureapp.domain.entities.Content
import com.example.brochureapp.domain.repository.BrochureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: BrochureRepository) :
    ViewModel() {

    private var contentListLiveData: MutableLiveData<List<Content>> = MutableLiveData(emptyList())
    private var isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(true)
    init {
        Log.e("HomeScreenViewModel", "init run")
        viewModelScope.launch {
            getBrochureListings()
        }
    }


    private fun getBrochureListings() {
        viewModelScope.launch {
            repository
                .getAllBrochures()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                contentListLiveData.value = listings
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            isLoadingLiveData.value = result.isLoading
                        }
                    }
                }
        }
    }

    fun getBrochureLists(): LiveData<List<Content>> {
        return contentListLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return isLoadingLiveData
    }
}